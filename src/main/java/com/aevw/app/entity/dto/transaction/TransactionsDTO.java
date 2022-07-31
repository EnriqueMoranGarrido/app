package com.aevw.app.entity.dto.transaction;

public class TransactionsDTO {

    private String dateTime;

    private String type;

    private Double value;

    public TransactionsDTO() {
    }

    public TransactionsDTO(String dateTime, String type, Double value) {
        this.dateTime = dateTime;
        this.type = type;
        this.value = value;
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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
