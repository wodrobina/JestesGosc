package eu.wodrobina.jestesgosc.controller;

import eu.wodrobina.jestesgosc.dto.DeleteUserDto;
import eu.wodrobina.jestesgosc.dto.EditUserDto;
import eu.wodrobina.jestesgosc.dto.NewUserDto;
import eu.wodrobina.jestesgosc.model.Gosc;
import eu.wodrobina.jestesgosc.model.User;
import eu.wodrobina.jestesgosc.repository.GoscRepository;
import eu.wodrobina.jestesgosc.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller()
public class UserController {

    private final UserRepository userRepository;
    private final GoscRepository goscRepository;

    public UserController(UserRepository userRepository, GoscRepository goscRepository) {
        this.userRepository = userRepository;
        this.goscRepository = goscRepository;
    }

    @GetMapping("/admin/gosc")
    public String getAllGosc(Model model) {
        getUsersSortedByName();

        return "list-gosc";
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
        goscRepository.findByUser_Email(deleteUserDto.getEmail())
                .ifPresent(goscRepository::delete);
//        userRepository.deleteByEmail(deleteUserDto.getEmail());
        fillModelWithAllUsers(model);
        return "admin";
    }

    @Transactional
    @PostMapping("/admin/users/edit")
    public String editUser(@ModelAttribute(value = "editUser") EditUserDto editUserDto, Model model) {
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
        User newUser = new User(newUserDto);
        userRepository.save(newUser);
        goscRepository.save(new Gosc(newUser));
        fillModelWithAllUsers(model);
        return "admin";
    }

    private void fillModelWithAllUsers(Model model) {
        model.addAttribute("users", getUsersSortedByName());
    }

    private List<User> getUsersSortedByName() {
        return userRepository.findAll().stream()
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());
    }

    private boolean requestedUserIsPresent(NewUserDto newUserDto) {
        return userRepository.findByEmail(newUserDto.getMail()).isPresent();
    }
}
