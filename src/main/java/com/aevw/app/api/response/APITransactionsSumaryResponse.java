package com.aevw.app.api.response;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;

public class APITransactionsSumaryResponse {

    private HttpStatus status;
    private Object transactions;
    private Object error;

    public APITransactionsSumaryResponse() {
        this.status = HttpStatus.OK;
    }

    public APITransactionsSumaryResponse(Object transactions, Object error) {
        this.status = HttpStatus.OK;
        this.transactions = transactions;
        this.error = error;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Object getTransactions() {
        return transactions;
    }

    public void setTransactions(Object transactions) {
        this.transactions = transactions;
    }

    public void setDataJson(JSONObject transactions){
        this.transactions = transactions;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }
}