package com.aevw.app.entity.dto;

import javax.validation.constraints.*;

public class TransactionInputDTO {
    @NotNull
    @NotBlank(message = "Value is mandatory")
    private Integer value;

    private String email;

    private String start_date;

    private String end_date;

    public TransactionInputDTO() {
    }

    public TransactionInputDTO(Integer value) {
        this.value = value;
    }

    public TransactionInputDTO(Integer value, String email) {
        this.value = value;
        this.email = email;
    }

    public TransactionInputDTO(String email) {
        this.email = email;
    }

    public TransactionInputDTO(String start_date, String end_date) {
        this.start_date = start_date;
        this.end_date = end_date;
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

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
