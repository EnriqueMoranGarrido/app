package com.aevw.app.entity.dto;

import javax.money.NumberValue;

public class InformationSummaryOutputDTO {

    NumberValue payments_received;

    NumberValue payments_made;

    NumberValue withdrawn;

    NumberValue filled;

    public InformationSummaryOutputDTO() {

    }

    public InformationSummaryOutputDTO(NumberValue payments_received, NumberValue payments_made, NumberValue withdrawn, NumberValue filled) {
        this.payments_received = payments_received;
        this.payments_made = payments_made;
        this.withdrawn = withdrawn;
        this.filled = filled;
    }

    public NumberValue getPayments_received() {
        return payments_received;
    }

    public void setPayments_received(NumberValue payments_received) {
        this.payments_received = payments_received;
    }

    public NumberValue getPayments_made() {
        return payments_made;
    }

    public void setPayments_made(NumberValue payments_made) {
        this.payments_made = payments_made;
    }

    public NumberValue getWithdrawn() {
        return withdrawn;
    }

    public void setWithdrawn(NumberValue withdrawn) {
        this.withdrawn = withdrawn;
    }

    public NumberValue getFilled() {
        return filled;
    }

    public void setFilled(NumberValue filled) {
        this.filled = filled;
    }

}
