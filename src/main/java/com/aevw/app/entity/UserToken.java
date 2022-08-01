package com.aevw.app.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserToken {
    @Id
    private String Id;

    private String token;

    private String userEmail;

    public UserToken(String token, String userEmail, String id) {
        this.token = token;
        this.userEmail = userEmail;
        this.Id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserToken userToken = (UserToken) o;
        return Id != null && Objects.equals(Id, userToken.Id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
