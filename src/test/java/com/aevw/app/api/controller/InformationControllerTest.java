package com.aevw.app.api.controller;

import com.aevw.app.api.response.APIResponse;
import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.dto.UserLogInRequest;
import com.aevw.app.entity.dto.information.InformationInputDTO;
import com.aevw.app.repository.UserRepository;
import com.aevw.app.service.information.InformationService;
import com.aevw.app.service.transaction.TransactionService;
import com.aevw.app.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;

@SpringBootTest
@Slf4j
class InformationControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired UserService userService;

    @Autowired TransactionService transactionService;

    @Autowired InformationService informationService;


    String testFirstName = "first";
    String testLastName = "last";
    String testEmail = "testmail@mail.com";
    String testPassword = "password";
    String startDate = "2022-01-01";
    String endDate = "2022-08-05";
    Double testValueToFill = 100.0;
    Double testValueToWithdraw = 50.0;


    private AppUser CreateTestUser(String email){

            AppUser user = new AppUser(
                    "id",
                    testFirstName,
                    testLastName,
                    LocalDate.of(1998,4,12),
                    email,
                    testPassword);
            userRepository.save(user);
            return user;
        }

    private String LoginUser(String email, String password){
            UserLogInRequest logInRequestTest = new UserLogInRequest(email,password);
            APIResponse apiResponseTest =  userService.logInUser(logInRequestTest);
            HashMap<String,String> logInToken = (HashMap<String, String>) apiResponseTest.getData();
            return logInToken.get("token");
        }

    private void FillMoney(AppUser user){
            transactionService.fill(user,testValueToFill);
        }
    private void WithdrawMoney(AppUser user){
        transactionService.withdraw(user,testValueToWithdraw);
    }
    private InformationInputDTO CreateInformationInputDTO(String currency){
            InformationInputDTO balance = new InformationInputDTO();
            balance.setCurrency(currency);
            balance.setStart_date(startDate);
            balance.setEnd_date(endDate);

            return balance;
        }


    //////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////           BALANCE            //////////////////////////////

    @Test
    void getBalanceNotNull() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO balance = CreateInformationInputDTO("USD");

        APIResponse apiResponseInformation =informationService.balance(balance,user);

        Assertions.assertNotNull(apiResponseInformation);

    }

    @Test
    void getBalance_DataNotNull() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO balance = CreateInformationInputDTO("USD");

        APIResponse apiResponseInformation =informationService.balance(balance,user);

        Assertions.assertNotNull(apiResponseInformation.getData());

    }

    @Test
    void getBalance_StatusNotNull() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO balance = CreateInformationInputDTO("USD");

        APIResponse apiResponseInformation =informationService.balance(balance,user);

        Assertions.assertNotNull(apiResponseInformation.getStatus());

    }

    @Test
    void getBalance_ErrorNull() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO balance = CreateInformationInputDTO("USD");

        APIResponse apiResponseInformation =informationService.balance(balance,user);

        Assertions.assertNull(apiResponseInformation.getError());

    }

    @Test
    void getBalance_DataValueHundred() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO balance = CreateInformationInputDTO("USD");

        APIResponse apiResponseInformation =informationService.balance(balance,user);

        Assertions.assertTrue(apiResponseInformation.getData().toString().contains("balance=100.0"));

    }

    @Test
    void getBalance_DataValueZero() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        InformationInputDTO balance = CreateInformationInputDTO("USD");

        APIResponse apiResponseInformation =informationService.balance(balance,user);

        Assertions.assertTrue(apiResponseInformation.getData().toString().contains("balance=0.0"));

    }

    @Test
    void getBalance_DataValueZero_EUR() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        InformationInputDTO balance = CreateInformationInputDTO("EUR");

        APIResponse apiResponseInformation =informationService.balance(balance,user);

        Assertions.assertTrue(apiResponseInformation.getData().toString().contains("balance=0.0"));

    }

    @Test
    void getBalance_DataValue_Not_Hundred_EUR() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO balance = CreateInformationInputDTO("EUR");

        APIResponse apiResponseInformation =informationService.balance(balance,user);

        Assertions.assertFalse(apiResponseInformation.getData().toString().contains("balance=100.0"));

    }

    @Test
    void getBalance_DataNotNull_EUR() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO balance = CreateInformationInputDTO("EUR");

        APIResponse apiResponseInformation =informationService.balance(balance,user);

        Assertions.assertNotNull(apiResponseInformation.getData());

    }

    @Test
    void getBalance_StatusNotNull_EUR() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO balance = CreateInformationInputDTO("EUR");

        APIResponse apiResponseInformation =informationService.balance(balance,user);

        Assertions.assertNotNull(apiResponseInformation.getStatus());

    }


    @Test
    void getBalance_DataValueZero_MXN() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        InformationInputDTO balance = CreateInformationInputDTO("MXN");

        APIResponse apiResponseInformation =informationService.balance(balance,user);

        Assertions.assertTrue(apiResponseInformation.getData().toString().contains("balance=0.0"));

    }

    @Test
    void getBalance_DataValue_Not_Hundred_MXN() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO balance = CreateInformationInputDTO("MXN");

        APIResponse apiResponseInformation =informationService.balance(balance,user);

        Assertions.assertFalse(apiResponseInformation.getData().toString().contains("balance=100.0"));

    }

    @Test
    void getBalance_DataNotNull_MXN() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO balance = CreateInformationInputDTO("MXN");

        APIResponse apiResponseInformation =informationService.balance(balance,user);

        Assertions.assertNotNull(apiResponseInformation.getData());

    }

    @Test
    void getBalance_StatusNotNull_MXN() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO balance = CreateInformationInputDTO("MXN");

        APIResponse apiResponseInformation =informationService.balance(balance,user);

        Assertions.assertNotNull(apiResponseInformation.getStatus());

    }

    @Test
    void getBalance_ErrorNull_MXN() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO balance = CreateInformationInputDTO("MXN");

        APIResponse apiResponseInformation =informationService.balance(balance,user);

        Assertions.assertNull(apiResponseInformation.getError());

    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////           SUMMARY            //////////////////////////////

    @Test
    void getSummary() {
        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO summary = CreateInformationInputDTO("USD");

        APIResponse apiResponseInformation =informationService.summary(summary,user);

        Assertions.assertNotNull(apiResponseInformation);
    }

    @Test
    void getSummary_DataNotNull() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO summary = CreateInformationInputDTO("USD");

        APIResponse apiResponseInformation =informationService.summary(summary,user);

        Assertions.assertNotNull(apiResponseInformation.getData());

    }

    @Test
    void getSummary_StatusNotNull() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO summary = CreateInformationInputDTO("USD");

        APIResponse apiResponseInformation =informationService.summary(summary,user);

        Assertions.assertNotNull(apiResponseInformation.getStatus());

    }

    @Test
    void getSummary_ErrorNull() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO summary = CreateInformationInputDTO("USD");

        APIResponse apiResponseInformation =informationService.summary(summary,user);

        Assertions.assertNull(apiResponseInformation.getError());

    }


    @Test
    void getSummary_DataNotNull_EUR() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO summary = CreateInformationInputDTO("EUR");

        APIResponse apiResponseInformation =informationService.summary(summary,user);

        Assertions.assertNotNull(apiResponseInformation.getData());

    }

    @Test
    void getSummary_StatusNotNull_EUR() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO summary = CreateInformationInputDTO("EUR");

        APIResponse apiResponseInformation =informationService.summary(summary,user);

        Assertions.assertNotNull(apiResponseInformation.getStatus());

    }

    @Test
    void getSummary_DataValue_Not_Hundred_MXN() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO summary = CreateInformationInputDTO("MXN");

        APIResponse apiResponseInformation =informationService.summary(summary,user);

        Assertions.assertFalse(apiResponseInformation.getData().toString().contains("payments_received=0.0, payments_made=0.0, withdrawn=0.0, filled=100.0"));

    }

    @Test
    void getSummary_DataNotNull_MXN() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO summary = CreateInformationInputDTO("MXN");

        APIResponse apiResponseInformation =informationService.summary(summary,user);

        Assertions.assertNotNull(apiResponseInformation.getData());

    }

    @Test
    void getSummary_StatusNotNull_MXN() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO summary = CreateInformationInputDTO("MXN");

        APIResponse apiResponseInformation =informationService.summary(summary,user);

        Assertions.assertNotNull(apiResponseInformation.getStatus());

    }

    @Test
    void getSummary_ErrorNull_MXN() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO summary = CreateInformationInputDTO("MXN");

        APIResponse apiResponseInformation =informationService.summary(summary,user);

        Assertions.assertNull(apiResponseInformation.getError());

    }

    @Test
    void getSummary_DataFillAndWithdraw_FalseWithdraw() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        FillMoney(user);
        WithdrawMoney(user);
        log.info(token);
        InformationInputDTO summary = CreateInformationInputDTO("USD");

        APIResponse apiResponseInformation =informationService.summary(summary,user);

        Assertions.assertFalse(apiResponseInformation.getData().toString().contains("withdrawn=25.0"));

    }

    @Test
    void getSummary_DataFillAndWithdraw_FalseFill() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        FillMoney(user);
        WithdrawMoney(user);
        log.info(token);
        InformationInputDTO summary = CreateInformationInputDTO("USD");

        APIResponse apiResponseInformation =informationService.summary(summary,user);

        Assertions.assertFalse(apiResponseInformation.getData().toString().contains("payments_received=0.0, payments_made=0.0, withdrawn=50.0, filled=50.0"));

    }

    //////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////              SERIES             ////////////////////////////
    @Test
    void getSeries() {
        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO series = CreateInformationInputDTO("USD");

        APIResponse apiResponseInformation =informationService.series(series,user);

        Assertions.assertNotNull(apiResponseInformation);
    }

    @Test
    void getSeries_DataNotNull() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO series = CreateInformationInputDTO("USD");

        APIResponse apiResponseInformation =informationService.series(series,user);

        Assertions.assertNotNull(apiResponseInformation.getData());

    }

    @Test
    void getSeries_StatusNotNull() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO series = CreateInformationInputDTO("USD");

        APIResponse apiResponseInformation =informationService.series(series,user);

        Assertions.assertNotNull(apiResponseInformation.getStatus());

    }

    @Test
    void getSeries_ErrorNull() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO series = CreateInformationInputDTO("USD");

        APIResponse apiResponseInformation =informationService.series(series,user);

        Assertions.assertNull(apiResponseInformation.getError());

    }

    @Test
    void getSeries_DataNotNull_EUR() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO series = CreateInformationInputDTO("EUR");

        APIResponse apiResponseInformation =informationService.series(series,user);

        Assertions.assertNotNull(apiResponseInformation.getData());

    }

    @Test
    void getSeries_StatusNotNull_EUR() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO series = CreateInformationInputDTO("EUR");

        APIResponse apiResponseInformation =informationService.series(series,user);

        Assertions.assertNotNull(apiResponseInformation.getStatus());

    }

    @Test
    void getSeries_DataNotNull_MXN() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO series = CreateInformationInputDTO("MXN");

        APIResponse apiResponseInformation =informationService.series(series,user);

        Assertions.assertNotNull(apiResponseInformation.getData());

    }

    @Test
    void getSeries_StatusNotNull_MXN() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO series = CreateInformationInputDTO("MXN");

        APIResponse apiResponseInformation =informationService.series(series,user);

        Assertions.assertNotNull(apiResponseInformation.getStatus());

    }

    @Test
    void getSeries_ErrorNull_MXN() {

        AppUser user = CreateTestUser(testEmail);
        String token = LoginUser(testEmail,testPassword);
        log.info(token);
        FillMoney(user);
        InformationInputDTO series = CreateInformationInputDTO("MXN");

        APIResponse apiResponseInformation =informationService.series(series,user);

        Assertions.assertNull(apiResponseInformation.getError());

    }

}