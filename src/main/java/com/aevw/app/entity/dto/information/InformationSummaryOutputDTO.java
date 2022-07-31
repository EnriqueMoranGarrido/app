package com.aevw.app.entity.dto.information;

public class InformationSummaryOutputDTO {

    Double payments_received = 0.0;

    Double payments_made = 0.0;

    Double withdrawn = 0.0;

    Double filled = 0.0;

    public InformationSummaryOutputDTO() {

    }

    public InformationSummaryOutputDTO(Double payments_received, Double payments_made, Double withdrawn, Double filled) {
        this.payments_received = payments_received;
        this.payments_made = payments_made;
        this.withdrawn = withdrawn;
        this.filled = filled;
    }

    public Double getPayments_received() {
        return payments_received;
    }

    public void setPayments_received(Double payments_received) {
        this.payments_received = payments_received;
    }

    public Double getPayments_made() {
        return payments_made;
    }

    public void setPayments_made(Double payments_made) {
        this.payments_made = payments_made;
    }

    public Double getWithdrawn() {
        return withdrawn;
    }

    public void setWithdrawn(Double withdrawn) {
        this.withdrawn = withdrawn;
    }

    public Double getFilled() {
        return filled;
    }

    public void setFilled(Double filled) {
        this.filled = filled;
    }

}
