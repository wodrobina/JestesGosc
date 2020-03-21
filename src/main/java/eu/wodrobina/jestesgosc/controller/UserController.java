package eu.wodrobina.jestesgosc.controller;

import eu.wodrobina.jestesgosc.dto.NewUser;
import eu.wodrobina.jestesgosc.model.User;
import eu.wodrobina.jestesgosc.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller()
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/admin")
    public String getAdmin(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin";
    }

//    @GetMapping("/users")
//    public String getUsers(Model model) {
//        model.addAttribute("newUser", new NewUser());
//        model.addAttribute("users", userRepository.findAll());
//        return "admin";
//    }

    @GetMapping("/admin/users/add")
    public String getAdd(Model model) {
        model.addAttribute("newUser", new NewUser());
        return "add-user";
    }

    @DeleteMapping("/admin/users/remove/{email}")
    public String removeUser(@PathVariable String email, Model model) {
        userRepository.deleteByEmail(email);
        model.addAttribute("users", userRepository.findAll());
        return "admin";
    }

    @PostMapping("/admin/users/add")
    public String createNewUser(@Valid @ModelAttribute(value = "newUser") NewUser newUser, Model model) {
        if (requestedUserIsPresent(newUser)) {
            throw new RuntimeException("User already exist");
        }
        userRepository.save(new User(newUser));
        model.addAttribute("users", userRepository.findAll());
        return "admin";
    }

    private boolean requestedUserIsPresent(NewUser newUser) {
        return userRepository.findByEmail(newUser.getMail()).isPresent();
    }
}
