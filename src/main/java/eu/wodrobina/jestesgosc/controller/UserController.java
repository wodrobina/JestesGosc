package eu.wodrobina.jestesgosc.controller;

import eu.wodrobina.jestesgosc.dto.DeleteUserDto;
import eu.wodrobina.jestesgosc.dto.EditUserDto;
import eu.wodrobina.jestesgosc.dto.NewUserDto;
import eu.wodrobina.jestesgosc.model.User;
import eu.wodrobina.jestesgosc.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller()
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/admin/users")
    public String getAdmin(Model model) {
        fillModelWithAllUsers(model);
        return "admin";
    }

    @GetMapping("/admin/users/add")
    public String getAdd(Model model) {
        model.addAttribute("newUser", new NewUserDto());
        return "add-user";
    }

    @GetMapping("/admin/users/edit")
    public String getEditUser(@ModelAttribute(value = "editUser") EditUserDto editUserDto, Model model) {
        Optional<User> editedUser = userRepository.findByEmail(editUserDto.getIdentifyingEmail());

        model.addAttribute("editUser", new EditUserDto(editedUser.orElseThrow()));
        return "edit-user";
    }

    @Transactional
    @PostMapping("/admin/users/remove")
    public String removeUser(@ModelAttribute(value = "deleteUser") DeleteUserDto deleteUserDto, Model model) {
        userRepository.deleteByEmail(deleteUserDto.getEmail());
        fillModelWithAllUsers(model);
        return "admin";
    }

    @Transactional
    @PostMapping("/admin/users/edit")
    public String editUser(@ModelAttribute(value = "edit") EditUserDto editUserDto, Model model) {
        Optional<User> editedUser = userRepository.findByEmail(editUserDto.getIdentifyingEmail());
        final User updatedUser = editedUser.orElseThrow();
        updatedUser.setName(editUserDto.getNewName());

        userRepository.save(updatedUser);
        fillModelWithAllUsers(model);
        return "admin";
    }

    @Transactional
    @PostMapping("/admin/users/add")
    public String createNewUser(@Valid @ModelAttribute(value = "newUser") NewUserDto newUserDto, Model model) {
        if (requestedUserIsPresent(newUserDto)) {
            throw new RuntimeException("User already exist");
        }
        userRepository.save(new User(newUserDto));
        fillModelWithAllUsers(model);
        return "admin";
    }

    private void fillModelWithAllUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
    }

    private boolean requestedUserIsPresent(NewUserDto newUserDto) {
        return userRepository.findByEmail(newUserDto.getMail()).isPresent();
    }
}
