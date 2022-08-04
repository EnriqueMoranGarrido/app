package com.aevw.app.entity.dto.information;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InformationSummaryOutputDTO {

    Double payments_received = 0.0;

    Double payments_made = 0.0;

    Double withdrawn = 0.0;

    Double filled = 0.0;

}
