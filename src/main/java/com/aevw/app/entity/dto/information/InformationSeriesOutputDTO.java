package com.aevw.app.entity.dto.information;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class InformationSeriesOutputDTO {

    Double[] payments_received;

    Double[] payments_made;

    Double[] withdrawn;

    Double[] filled;

    String[] dates;

}
