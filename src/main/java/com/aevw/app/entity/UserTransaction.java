package com.aevw.app.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity @RequiredArgsConstructor
@Getter
@Setter
@ToString
public class UserTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer transaction_number;
    private String dateTime;

    private String email;

    private Double money = 0.0;

    private String type;

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
