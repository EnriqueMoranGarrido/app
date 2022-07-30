package com.aevw.app.entity.dto;

import javax.money.NumberValue;
import java.util.ArrayList;

public class InformationSeriesOutputDTO {

    NumberValue payments_received;

    NumberValue payments_made;

    NumberValue withdrawn;

    NumberValue filled;

    ArrayList<String> dates;

    public InformationSeriesOutputDTO() {
    }

    public InformationSeriesOutputDTO(NumberValue payments_received, NumberValue payments_made, NumberValue withdrawn, NumberValue filled, ArrayList<String> dates) {
        this.payments_received = payments_received;
        this.payments_made = payments_made;
        this.withdrawn = withdrawn;
        this.filled = filled;
        this.dates = dates;
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

    public ArrayList<String> getDates() {
        return dates;
    }

    public void setDates(ArrayList<String> dates) {
        this.dates = dates;
    }
}
