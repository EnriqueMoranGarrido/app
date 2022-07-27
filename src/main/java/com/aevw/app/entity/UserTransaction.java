package com.aevw.app.entity;

import javax.persistence.*;
import java.util.HashMap;

@Entity
public class UserTransaction {

//    @GeneratedValue(strategy = GenerationType.AUTO)

    @Id
    @Column(name = "date_time", nullable = false)
    private String dateTime;

//    @Column(name = "token", nullable = false)

    private String email;

    private Double money = 0.0;

    private String type;



    public UserTransaction() {
    }

    public UserTransaction( String email, Double money, String dateTime, String type) {
        this.dateTime = dateTime;
        this.email = email;
        this.money = money;
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



    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
