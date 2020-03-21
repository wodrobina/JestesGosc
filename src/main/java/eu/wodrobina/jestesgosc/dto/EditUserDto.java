package eu.wodrobina.jestesgosc.dto;

import eu.wodrobina.jestesgosc.model.User;

public class EditUserDto {

    private String identifyingEmail;
    private String newName;

    public EditUserDto() {
    }

    public EditUserDto(User user) {
        this.identifyingEmail = user.getEmail();
        this.newName = user.getName();
    }

    public String getIdentifyingEmail() {
        return identifyingEmail;
    }

    public void setIdentifyingEmail(String identifyingEmail) {
        this.identifyingEmail = identifyingEmail;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
