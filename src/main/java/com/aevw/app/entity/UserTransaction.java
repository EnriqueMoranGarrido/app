package com.aevw.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.HashMap;

@Entity
public class UserTransaction {
    @Id
    @Column(name = "token", nullable = false)
    private String token;

    private String email;

    private Double money = 0.0;

    private String dateTime;

    private String type;



    public UserTransaction() {
    }

    public UserTransaction(String token, String email, Double money, String dateTime, String type) {
        this.token = token;
        this.email = email;
        this.money = money;
        this.dateTime = dateTime;
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
