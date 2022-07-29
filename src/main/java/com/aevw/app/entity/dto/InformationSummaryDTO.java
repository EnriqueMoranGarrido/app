package com.aevw.app.entity.dto;

public class InformationSummaryDTO {
    private String currency;

    private String start_date;

    private String end_date;

    public InformationSummaryDTO() {
    }

    public InformationSummaryDTO(String currency, String start_date, String end_date) {
        this.currency = currency;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}