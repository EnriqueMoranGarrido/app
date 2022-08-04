package com.aevw.app.entity.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.*;

@AllArgsConstructor
@Data
@RequiredArgsConstructor
public class TransactionInputDTO {
    @NotNull
    @NotBlank(message = "Value is mandatory")
    private Integer value;

    private String email;

    private String start_date;

    private String end_date;



    public TransactionInputDTO(Integer value) {
        this.value = value;
    }

    public TransactionInputDTO(Integer value, String email) {
        this.value = value;
        this.email = email;
    }

    public TransactionInputDTO(String email) {
        this.email = email;
    }

    public TransactionInputDTO(String start_date, String end_date) {
        this.start_date = start_date;
        this.end_date = end_date;
    }

}
