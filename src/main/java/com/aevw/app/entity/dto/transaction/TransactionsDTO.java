package com.aevw.app.entity.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Data
public class TransactionsDTO {

    @NotBlank
    @NotBlank
    private String dateTime;

    @NotBlank
    @NotBlank
    private String type;

    @NotBlank
    @NotBlank
    private Double value;

}
