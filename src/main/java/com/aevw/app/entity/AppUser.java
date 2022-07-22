package com.aevw.app.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.TimeZone;

@Entity
//@Table(name="users")
public class AppUser {

    @Id
    private String id;
    @NotBlank(message = "First name is mandatory")
    @Size(min = 2, message = "First name should have at least 2 characters")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    @Size(min = 2, message = "Last name should have at least 2 characters")
    private String lastName;

    @Past(message = "Birthdate should be past")
    private LocalDate birthDate;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "email should be a valid email format")
    @Column(name = "email", unique = true)
    private String email;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 2, message = "Password should have at least 2 characters")
    private String password;

    @Transient
    private Integer age;

    public Map<String, String> getToken() {
        return token;
    }

    public void setToken(Map<String, String> token) {
        this.token = token;
    }

    @Transient
    @JsonFormat
    private Map<String,String> token;

    private Boolean isActive = true;

    private Integer loginCount;

    private String ssoType;

    private ZonedDateTime loginAt;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private String userType;

    public AppUser() {

    }



    public AppUser(String id, String firstName, String lastName, LocalDate birthDate, String email, String password, Integer age) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.password = passwordEncoder.encode(password);
        this.age = age;
    }

    public AppUser(String firstName, String lastName, LocalDate birthDate, String email, String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.password = passwordEncoder.encode(password);
    }

    public AppUser(String id, String firstName, String lastName, LocalDate birthDate, String email, String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.password = passwordEncoder.encode(password);
    }




    public Integer getAge() {
        return Period.between(this.birthDate,LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }
}
