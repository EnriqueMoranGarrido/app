package com.aevw.app.entity.dto.information;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InformationInputDTO {
    private String currency;

    private String start_date;

    private String end_date;


    public InformationInputDTO(String currency) {
        this.currency = currency;
    }


}
