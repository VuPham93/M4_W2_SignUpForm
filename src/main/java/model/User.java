package model;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Component
public class User implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @NotEmpty
    @Size(min = 2, max = 45)
    private String firstName;

    @NotEmpty
    @Size(min = 2, max = 45)
    private String lastName;

    private String phoneNumber;

    @Min(18)
    private int age;

    private String email;

    public User() {
    }

    public User(Long id, @NotEmpty @Size(min = 2, max = 45) String firstName, @NotEmpty @Size(min = 2, max = 45) String lastName, String phoneNumber, @Min(18) int age, String email) {
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.email = email;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        String phoneNumber = user.getPhoneNumber();
        String email = user.getEmail();

        ValidationUtils.rejectIfEmpty(errors, "phoneNumber", phoneNumber);
        ValidationUtils.rejectIfEmpty(errors, "email", email);

        if (phoneNumber.length() != 10) {
            errors.rejectValue("phoneNumber", "phoneNumber.length");
        }
        if (!phoneNumber.startsWith("0")) {
            errors.rejectValue("phoneNumber", "phoneNumber.startsWith");
        }
        if (!phoneNumber.matches("(^$|[0-9]*$)")) {
            errors.rejectValue("phoneNumber", "phoneNumber.matches");
        }
        if (!email.matches("^[a-zA-Z][a-zA-Z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$")) {
            errors.rejectValue("email", "email.matches");
        }
    }
}
