package eu.wodrobina.jestesgosc.controller;

import eu.wodrobina.jestesgosc.model.Gosc;
import eu.wodrobina.jestesgosc.repository.GoscRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

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
        Gosc latestGosc = goscRepository.findByToPeriodIsNull();
        model.addAttribute("gosc", latestGosc);
        return "index";
    }

    @GetMapping(value = "/logout")
    public String logout() throws ServletException {
        request.logout();
        return "redirect:/";
    }
}
