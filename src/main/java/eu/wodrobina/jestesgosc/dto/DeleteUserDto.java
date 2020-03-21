package eu.wodrobina.jestesgosc.dto;

public class DeleteUserDto {
    private final String email;

    public DeleteUserDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
