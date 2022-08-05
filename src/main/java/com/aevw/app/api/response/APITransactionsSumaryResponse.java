package com.aevw.app.api.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
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

}
