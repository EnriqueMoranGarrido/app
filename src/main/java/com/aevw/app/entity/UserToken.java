package com.aevw.app.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer token_id;
    private String userEmail;

    private String token;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserToken userToken = (UserToken) o;
        return userEmail != null && Objects.equals(userEmail, userToken.userEmail);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
