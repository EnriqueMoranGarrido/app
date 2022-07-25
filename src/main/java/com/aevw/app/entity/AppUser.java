package com.aevw.app.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.*;
import javax.validation.constraints.*;
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
    @NotNull
    @NotBlank(message = "First name is mandatory")
    @Size(min = 2, message = "First name should have at least 2 characters")

    private String firstName;
    @NotNull
    @NotBlank(message = "Last name is mandatory")
    @Size(min = 2, message = "Last name should have at least 2 characters")
    private String lastName;

    @NotNull
    @Past(message = "Birthdate should be past")
    private LocalDate birthDate;

    @NotNull
    @NotBlank(message = "Email is mandatory")
    @Email(message = "email should be a valid email format")
    @Column( unique = true)
    private String email;
    @NotNull
    @NotBlank(message = "Password is mandatory")
    @Size(min = 2, message = "Password should have at least 2 characters")
    private String password;

    @Transient
    private Integer age;

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    @Transient
    @JsonFormat
    private Object token;

    private Boolean isActive = true;

    @Transient
    private Integer loginCount;

    @Transient
    private String ssoType;

    @Transient
    private ZonedDateTime loginAt;

    @Transient
    private ZonedDateTime createdAt;

    @Transient
    private ZonedDateTime updatedAt;

    @Transient
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }


}
