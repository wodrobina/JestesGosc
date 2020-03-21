package eu.wodrobina.jestesgosc.dto;

public class NewUserDto {

    private String name;
    private String mail;

    public NewUserDto() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }
}
