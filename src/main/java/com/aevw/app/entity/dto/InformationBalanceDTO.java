package com.aevw.app.entity.dto;

public class InformationBalanceDTO {

    private String currency;

    public InformationBalanceDTO() {
    }

    public InformationBalanceDTO(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
