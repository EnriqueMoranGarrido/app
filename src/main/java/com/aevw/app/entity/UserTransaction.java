package com.aevw.app.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity @RequiredArgsConstructor @AllArgsConstructor
@Getter
@Setter
@ToString
public class UserTransaction {

    @Id
    private String dateTime;

    private String email;

    private Double money = 0.0;

    private String type;

    public UserTransaction( String email, Double money, String dateTime, String type) {
        this.dateTime = dateTime;
        this.email = email;
        this.money = money;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserTransaction that = (UserTransaction) o;
        return dateTime != null && Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
