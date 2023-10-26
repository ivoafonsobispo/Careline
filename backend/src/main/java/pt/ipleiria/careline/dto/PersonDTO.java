package pt.ipleiria.careline.dto;

public class PersonDTO {
    public String name;
    public String email;
    public Integer age;

    public PersonDTO(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public PersonDTO() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }

}
