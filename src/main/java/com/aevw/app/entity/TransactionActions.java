package com.aevw.app.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TransactionActions {
    @NotNull
    @NotBlank(message = "Value is mandatory")
    private Integer value;

    private String email;

    public TransactionActions() {
    }

    public TransactionActions(Integer value) {
        this.value = value;
    }

    public TransactionActions(Integer value, String email) {
        this.value = value;
        this.email = email;
    }

    public TransactionActions(String email) {
        this.email = email;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
