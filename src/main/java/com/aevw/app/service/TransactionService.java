package com.aevw.app.service;

import com.aevw.app.api.APIResponse;
import com.aevw.app.api.TransactionsSumaryResponse;

public interface TransactionService {

    APIResponse fill(String token, Double value);

    APIResponse withdraw(String token, Double value);

    APIResponse pay(String token, Double value, String email);

    TransactionsSumaryResponse getTransactions(String token, String start_date, String end_date);
}
