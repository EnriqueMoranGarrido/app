package com.aevw.app.utils;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.NumberValue;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;

public class CurrencyConverter {

    public CurrencyConverter() {
    }

    public NumberValue getMonetaryValue(String currency, Double userMoney){
        MonetaryAmount money = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(userMoney).create();
        CurrencyConversion conversion = MonetaryConversions.getConversion(currency);

        return money.with(conversion).getNumber();

    }
}
