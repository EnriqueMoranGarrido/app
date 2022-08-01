package com.aevw.app.service.transaction;

import com.aevw.app.api.response.APIResponse;
import com.aevw.app.api.response.APITransactionsSumaryResponse;
import com.aevw.app.entity.AppUser;

public interface TransactionService {

    APIResponse fill(AppUser fillUser, Double value) throws InterruptedException;

    APIResponse withdraw(AppUser withdrawUser, Double value) throws InterruptedException;

    APIResponse pay(AppUser payingUser, Double value, String email) throws InterruptedException;

    APITransactionsSumaryResponse getTransactions(AppUser getUser, String start_date, String end_date);
}
