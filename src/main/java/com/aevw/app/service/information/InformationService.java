package com.aevw.app.service.information;

import com.aevw.app.api.response.APIResponse;
import com.aevw.app.entity.dto.information.InformationInputDTO;

public interface InformationService {

    APIResponse balance(String token, InformationInputDTO balance);

    APIResponse summary(String token, InformationInputDTO summary);

    APIResponse series(String token, InformationInputDTO series);
}
