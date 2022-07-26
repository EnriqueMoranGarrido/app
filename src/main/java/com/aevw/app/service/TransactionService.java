package com.aevw.app.service;

import com.aevw.app.api.APIResponse;

public interface TransactionService {

    APIResponse fill(String token, Double value);

    APIResponse withdraw(String token, Double value);

    APIResponse pay();

    void getTransactions();
}
