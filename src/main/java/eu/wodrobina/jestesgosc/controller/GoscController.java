package eu.wodrobina.jestesgosc.controller;

import eu.wodrobina.jestesgosc.dto.SuccessorDto;
import eu.wodrobina.jestesgosc.model.Gosc;
import eu.wodrobina.jestesgosc.repository.GoscRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Optional;

@Controller
public class GoscController {

    private final HttpServletRequest request;
    private final GoscRepository goscRepository;

    public GoscController(HttpServletRequest request, GoscRepository goscRepository) {
        this.request = request;
        this.goscRepository = goscRepository;
    }

    @GetMapping("/")
    public String getHome(ModelMap model) {
        Optional<Gosc> currentGosc = goscRepository.findByFromPeriodIsNotNullAndToPeriodIsNull();
        model.addAttribute("gosc", currentGosc.orElse(null));
        return "index";
    }

    //refactor this monster
    @Transactional
    @PostMapping("/admin/gosc/successor")
    public String setSuccesor(@ModelAttribute(value = "successorGosc") SuccessorDto successorDto, Model model) {
        Gosc successorGosc = goscRepository.findByUser_Email(successorDto.getEmail())
                .orElseThrow();
        Optional<Gosc> optCurrentGosc = goscRepository.findByFromPeriodIsNotNullAndToPeriodIsNull();

        if (optCurrentGosc.isPresent()) {
            Gosc currentGosc = optCurrentGosc.get();
            if (successorGosc != currentGosc) {
                currentGosc.setSuccessor(successorGosc);
            } else {
                model.addAttribute("gosc", optCurrentGosc);
                return "index";
            }
        }

        if (successorGosc.getFromPeriod() == null) {
            successorGosc.succession(Instant.now());
        }
        model.addAttribute("gosc", successorGosc);
        return "index";
    }

    @GetMapping(value = "/logout")
    public String logout() throws ServletException {
        request.logout();
        return "redirect:/";
    }
}
