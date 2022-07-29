package com.aevw.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
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

    private Double capital = 0.0;

    @JsonFormat
    private String token ="";

    public AppUser() {

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

    public Double getCapital() {
        return capital;
    }

    public void setCapital(Double capital) {
        this.capital = capital;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
