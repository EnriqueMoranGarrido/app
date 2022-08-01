package com.aevw.app.service.information;

import com.aevw.app.api.response.APIResponse;
import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.dto.information.InformationInputDTO;

public interface InformationService {

    APIResponse balance(InformationInputDTO balance, AppUser balanceUser);

    APIResponse summary(InformationInputDTO summary, AppUser summaryUser);

    APIResponse series(InformationInputDTO series, AppUser seriesUser);
}
