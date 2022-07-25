package com.aevw.app.api;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;

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

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setDataJson(JSONObject data){
        this.data = data;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

}