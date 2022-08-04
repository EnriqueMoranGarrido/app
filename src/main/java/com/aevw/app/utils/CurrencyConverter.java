package com.aevw.app.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.Maps;


import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class CurrencyConverter {

    public CurrencyConverter() {
    }

    private final HashMap<String,Double> currencyMap = Maps.newHashMap();
    MonetaryAmount money = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(1).create();

    public Double getMonetaryValue(String currency, Double userMoney){

        getCurrencyExchanges();

        CacheLoader<String, String> loader;
        loader = new CacheLoader<>() {
            @Override
            public String load(String key) {
                return key.toUpperCase();
            }
        };


        Cache cache;
        cache = CacheBuilder.newBuilder().expireAfterAccess(2,TimeUnit.MILLISECONDS)
                .build(loader);

        cache.getUnchecked(currency);

        if (currencyMap.containsKey(currency)) {
            return currencyMap.get(currency) * userMoney;
        }
        CurrencyConversion conversion = MonetaryConversions.getConversion(currency);
        Double numberValue = money.with(conversion).getNumber().doubleValue();
        currencyMap.put(currency,numberValue);
        return numberValue*userMoney;
    }

    private void getCurrencyExchanges() {
        CurrencyConversion conversion = MonetaryConversions.getConversion("USD");
        Double numberValue = money.with(conversion).getNumber().doubleValue();
    }
}
