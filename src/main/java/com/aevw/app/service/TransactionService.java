package com.aevw.app.service;

import com.aevw.app.api.APIResponse;

public interface TransactionService {

    APIResponse fill(String token, Double value);

    APIResponse withdraw(String token, Double value);

    APIResponse pay(String token, Double value, String email);

    APIResponse getTransactions(String token, String start_date, String end_date);
}
