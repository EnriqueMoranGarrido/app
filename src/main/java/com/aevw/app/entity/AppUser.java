package com.aevw.app.entity;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
//@Table(name="users")
public class AppUser {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;

    private String password;

    @Transient
    private Integer age;

    public AppUser() {

    }

    public AppUser(String id, String firstName, String lastName, LocalDate birthDate, String email, String password, Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.age = age;
    }

    public AppUser(String firstName, String lastName, LocalDate birthDate, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
    }

    public AppUser(String id, String firstName, String lastName, LocalDate birthDate, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
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
        this.password = password;
    }
}
