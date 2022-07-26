package com.aevw.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class GlobalTransaction {
    @Id
    @Column(name = "email", nullable = false)
    private String email;

    private LocalDate transactionDate;

    private String transactionDestination;

    public GlobalTransaction() {
    }

    public GlobalTransaction(String email, LocalDate transactionDate, String transactionDestination) {
        this.email = email;
        this.transactionDate = transactionDate;
        this.transactionDestination = transactionDestination;
    }

    public String getTransactionDestination() {
        return transactionDestination;
    }

    public void setTransactionDestination(String transactionDestination) {
        this.transactionDestination = transactionDestination;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}
