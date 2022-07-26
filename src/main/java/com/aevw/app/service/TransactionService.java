package com.aevw.app.service;

import com.aevw.app.api.APIResponse;

public interface TransactionService {

    APIResponse fill(String token, Integer value);

    APIResponse withdraw();

    APIResponse pay();

    void getTransactions();
}
