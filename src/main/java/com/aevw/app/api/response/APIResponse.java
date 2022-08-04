package com.aevw.app.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class APIResponse {

    private HttpStatus status;
    private Object data;
    private Object error;

    public APIResponse() {
        this.status = HttpStatus.OK;
    }

    public APIResponse(Object data, Object error) {
        this.status = HttpStatus.OK;
        this.data = data;
        this.error = error;
    }

}