package com.aevw.app.service.transaction;

import com.aevw.app.api.response.APIResponse;
import com.aevw.app.api.response.APITransactionsSumaryResponse;

public interface TransactionService {

    APIResponse fill(String token, Double value);

    APIResponse withdraw(String token, Double value);

    APIResponse pay(String token, Double value, String email);

    APITransactionsSumaryResponse getTransactions(String token, String start_date, String end_date);
}
