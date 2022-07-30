package com.aevw.app.entity.dto;

import javax.money.NumberValue;

public class InformationBalanceOutputDTO {

    NumberValue balance;

    public InformationBalanceOutputDTO() {
    }

    public InformationBalanceOutputDTO(NumberValue balance) {
        this.balance = balance;
    }

    public NumberValue getBalance() {
        return balance;
    }

    public void setBalance(NumberValue balance) {
        this.balance = balance;
    }
}
