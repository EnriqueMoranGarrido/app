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

    @Transient
    private HashMap<String,String> transactions;


    public UserTransaction() {
    }

    public UserTransaction(String token, String email, Double money, HashMap<String,String> transactions) {
        this.token = token;
        this.email = email;
        this.money = money;
        this.transactions = transactions;
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

    public HashMap<String,String> getTransactions() {
        return transactions;
    }

    public void setTransactions(HashMap<String,String> transactions) {
        this.transactions = transactions;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
