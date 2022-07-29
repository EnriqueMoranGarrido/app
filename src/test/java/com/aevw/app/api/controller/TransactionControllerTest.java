package com.aevw.app.api.controller;

import com.aevw.app.api.response.APIResponse;
import com.aevw.app.api.response.APITransactionsSumaryResponse;
import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.UserTransaction;
import com.aevw.app.entity.dto.UserLogInRequest;
import com.aevw.app.repository.UserRepository;
import com.aevw.app.repository.UserTokenRepository;
import com.aevw.app.repository.UserTransactionRepository;
import com.aevw.app.service.transaction.TransactionService;
import com.aevw.app.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionControllerTest {

    @Autowired private UserRepository userRepository;

    @Autowired private UserTokenRepository userTokenRepository;

    @Autowired private UserTransactionRepository userTransactionRepository;

    @Autowired
    UserService userService;

    @Autowired
    TransactionService transactionService;


    String testFirstName = "firt";
    String testLastName = "last";
    String testEmail = "testFill1@mail.com";
    String testEmailTwo = "testoPay@mail.com";
    String testPassword = "password";
    String startDate = "2022-01-01";
    String endDate = "2022-07-29";
    Double testValueToFill = 100.0;
    Double testValueToWithdraw = 50.0;


    private String CreateAndLogInTestUser(){


        AppUser testUser = new AppUser("50",testFirstName,testLastName, LocalDate.of(1998,4,12),testEmail,testPassword);
        AppUser testUser2 = new AppUser("51",testFirstName,testLastName, LocalDate.of(1998,4,12),testEmailTwo,testPassword);

        userRepository.save(testUser);
        userRepository.save(testUser2);

        UserLogInRequest logInRequestTest = new UserLogInRequest(testEmail,testPassword);
        APIResponse apiResponseTest =  userService.logInUser(logInRequestTest);
        HashMap<String,String> logInToken = (HashMap<String, String>) apiResponseTest.getData();

        return logInToken.get("token");
    }

    private String getNewDate(){
        String dateString = LocalDateTime.now().toString();
        String testMinutes = String.valueOf(Integer.parseInt(dateString.split("-")[2].split(":")[1]) - 1);

        return dateString.replace(
                String.valueOf(Integer.parseInt(dateString.split("-")[2].split(":")[1])) ,
                testMinutes);

    }


    //////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////        FILL         ////////////////////////////////////
    @Test
    void fillTransactionNotNull() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponse = transactionService.fill(token,testValueToFill);

        Assertions.assertNotNull(realApiResponse);

    }

    @Test
    void fillTransaction_InstanceOfObject() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponse = transactionService.fill(token,testValueToFill);

        Assertions.assertInstanceOf(Object.class,realApiResponse);

    }

    @Test
    void fillTransaction_Data_InstanceOfString() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponse = transactionService.fill(token,testValueToFill);

        Assertions.assertInstanceOf(String.class,realApiResponse.getData());

    }

    @Test
    void fillTransaction_Status_InstanceOfHttp() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponse = transactionService.fill(token,testValueToFill);

        Assertions.assertInstanceOf(HttpStatus.class,realApiResponse.getStatus());

    }

    @Test
    void fillTransaction_Error_InstanceOfNull_WhenPasses() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponse = transactionService.fill(token,testValueToFill);

        Assertions.assertNull(realApiResponse.getError());

    }

    @Test
    void fillTransaction_CorrectValue() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponse = transactionService.fill(token,testValueToFill);
        String[] responseData = realApiResponse.getData().toString().split(" ");
        Double realValue =  Double.valueOf(responseData[0]);

        Assertions.assertEquals(realValue,testValueToFill);

    }

    @Test
    void fillTransaction_NotWrongValue() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponse = transactionService.fill(token,testValueToFill);
        String[] responseData = realApiResponse.getData().toString().split(" ");
        double realValue = Double.parseDouble(responseData[0]);

        assertNotEquals(50.0, realValue, 0.0);

    }

    @Test
    void fillTransaction_ValueNotNull() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponse = transactionService.fill(token,testValueToFill);
        String[] responseData = realApiResponse.getData().toString().split(" ");
        Double realValue =  Double.valueOf(responseData[0]);

        Assertions.assertNotNull(realValue);

    }

    @Test
    void fillTransaction_DoesNotThrowException() {

        String token = CreateAndLogInTestUser();

        Assertions.assertDoesNotThrow(()-> {
            APIResponse realApiResponse =
                    transactionService.fill(token,testValueToFill);});
    }

    @Test
    void fillTransaction_StoredTransaction() {

        String token = CreateAndLogInTestUser();

        String testDate = getNewDate();

        APIResponse realApiResponse = transactionService.fill(token,testValueToFill);
        List<UserTransaction> verifyTransaction = userTransactionRepository.findAllByDateTimeBetweenAndEmail(
                testDate,LocalDateTime.now().toString(),testEmail);

        Assertions.assertNotNull(verifyTransaction);

    }


    @Test
    void fillTransaction_StoredTransaction_ContentNotNull() {

        String token = CreateAndLogInTestUser();

        String testDate = getNewDate();

        APIResponse realApiResponse = transactionService.fill(token,testValueToFill);
        List<UserTransaction> verifyTransaction = userTransactionRepository.findAllByDateTimeBetweenAndEmail(
                testDate,LocalDateTime.now().toString(),testEmail);

        Assertions.assertNotNull(verifyTransaction);
        for (UserTransaction transaction: verifyTransaction
        ) {Assertions.assertNotNull(transaction);}

    }


    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////        WITHDRAW         //////////////////////////////////

    @Test
    void withdrawTransactionNotNull() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponseFill = transactionService.fill(token,testValueToFill);
        APIResponse realApiResponseWithdraw = transactionService.withdraw(token,testValueToWithdraw);

        Assertions.assertNotNull(realApiResponseWithdraw);

    }

    @Test
    void withdrawTransaction_InstanceOfObject() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponseFill = transactionService.fill(token,testValueToFill);
        APIResponse realApiResponseWithdraw = transactionService.withdraw(token,testValueToWithdraw);

        Assertions.assertInstanceOf(Object.class,realApiResponseWithdraw);

    }

    @Test
    void withdrawTransaction_Data_InstanceOfString() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponseFill = transactionService.fill(token,testValueToFill);
        APIResponse realApiResponseWithdraw = transactionService.withdraw(token,testValueToWithdraw);

        Assertions.assertInstanceOf(String.class,realApiResponseWithdraw.getData());

    }

    @Test
    void withdrawTransaction_Status_InstanceOfHttp() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponseFill = transactionService.fill(token,testValueToFill);
        APIResponse realApiResponseWithdraw = transactionService.withdraw(token,testValueToWithdraw);

        Assertions.assertInstanceOf(HttpStatus.class,realApiResponseWithdraw.getStatus());

    }

    @Test
    void withdrawTransaction_Error_InstanceOfNull_WhenPasses() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponseFill = transactionService.fill(token,testValueToFill);
        APIResponse realApiResponseWithdraw = transactionService.withdraw(token,testValueToWithdraw);

        Assertions.assertNull(realApiResponseWithdraw.getError());

    }

    @Test
    void withdrawTransaction_CorrectValue() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponse = transactionService.fill(token,testValueToFill);
        APIResponse realApiResponseWithdraw = transactionService.withdraw(token,testValueToWithdraw);
        String[] responseData = realApiResponseWithdraw.getData().toString().split(" ");
        Double realValue =  Double.valueOf(responseData[0]);

        Assertions.assertEquals(realValue,testValueToWithdraw);

    }

    @Test
    void withdrawTransaction_NotWrongValue() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponse = transactionService.fill(token,testValueToFill);
        APIResponse realApiResponseWithdraw = transactionService.withdraw(token,testValueToWithdraw);
        String[] responseData = realApiResponseWithdraw.getData().toString().split(" ");
        double realValue = Double.parseDouble(responseData[0]);

        assertNotEquals(2.0, realValue, 0.0);

    }

    @Test
    void withdrawTransaction_ValueNotNull() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponse = transactionService.fill(token,testValueToFill);
        APIResponse realApiResponseWithdraw = transactionService.withdraw(token,testValueToWithdraw);
        String[] responseData = realApiResponseWithdraw.getData().toString().split(" ");
        Double realValue =  Double.valueOf(responseData[0]);

        Assertions.assertNotNull(realValue);

    }

    @Test
    void withdrawTransaction_DoesNotThrowException() {

        String token = CreateAndLogInTestUser();

        Assertions.assertDoesNotThrow(()-> {
            APIResponse realApiResponse = transactionService.fill(token,testValueToFill);
            APIResponse realApiResponseWithdraw = transactionService.withdraw(token,testValueToWithdraw);
        });
    }

    @Test
    void withdrawTransaction_StoredTransaction() {

        String token = CreateAndLogInTestUser();

        String testDate = getNewDate();

        APIResponse realApiResponse = transactionService.fill(token,testValueToFill);
        APIResponse realApiResponseWithdraw = transactionService.withdraw(token,testValueToWithdraw);
        List<UserTransaction> verifyTransaction = userTransactionRepository.findAllByDateTimeBetweenAndEmail(
                testDate,LocalDateTime.now().toString(),testEmail);

        Assertions.assertNotNull(verifyTransaction);

    }

    @Test
    void withdrawTransaction_StoredTransaction_ContentNotNull() {

        String token = CreateAndLogInTestUser();

        String testDate = getNewDate();

        APIResponse realApiResponse = transactionService.fill(token,testValueToFill);
        APIResponse realApiResponseWithdraw = transactionService.withdraw(token,testValueToWithdraw);
        List<UserTransaction> verifyTransaction = userTransactionRepository.findAllByDateTimeBetweenAndEmail(
                testDate,LocalDateTime.now().toString(),testEmail);

        Assertions.assertNotNull(verifyTransaction);
        for (UserTransaction transaction: verifyTransaction
        ) {Assertions.assertNotNull(transaction);}

    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////        PAY         ////////////////////////////////////

    @Test
    void payTransactionNotNull() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponseFill = transactionService.fill(token,testValueToFill);
        APIResponse realApiResponsePay = transactionService.pay(token,testValueToWithdraw,testEmailTwo);

        Assertions.assertNotNull(realApiResponsePay);
    }

    @Test
    void payTransaction_InstanceOfObject() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponseFill = transactionService.fill(token,testValueToFill);
        APIResponse realApiResponsePay = transactionService.pay(token,testValueToWithdraw,testEmailTwo);

        Assertions.assertInstanceOf(Object.class,realApiResponsePay);

    }

    @Test
    void payTransaction_Data_InstanceOfString() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponseFill = transactionService.fill(token,testValueToFill);
        APIResponse realApiResponsePay = transactionService.pay(token,testValueToWithdraw,testEmailTwo);

        Assertions.assertInstanceOf(String.class,realApiResponsePay.getData());

    }

    @Test
    void payTransaction_Status_InstanceOfHttp() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponseFill = transactionService.fill(token,testValueToFill);
        APIResponse realApiResponsePay = transactionService.pay(token,testValueToWithdraw,testEmailTwo);

        Assertions.assertInstanceOf(HttpStatus.class,realApiResponsePay.getStatus());

    }

    @Test
    void payTransaction_Error_InstanceOfNull_WhenPasses() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponseFill = transactionService.fill(token,testValueToFill);
        APIResponse realApiResponsePay = transactionService.pay(token,testValueToWithdraw,testEmailTwo);

        Assertions.assertNull(realApiResponsePay.getError());

    }

    @Test
    void payTransaction_CorrectValue() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponse = transactionService.fill(token,testValueToFill);
        APIResponse realApiResponsePay = transactionService.pay(token,testValueToWithdraw,testEmailTwo);
        String[] responseData = realApiResponsePay.getData().toString().split(" ");
        Double realValue =  Double.valueOf(responseData[0]);

        Assertions.assertEquals(realValue,testValueToWithdraw);

    }

    @Test
    void payTransaction_NotWrongValue() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponse = transactionService.fill(token,testValueToFill);
        APIResponse realApiResponsePay = transactionService.pay(token,testValueToWithdraw,testEmailTwo);
        String[] responseData = realApiResponsePay.getData().toString().split(" ");
        double realValue = Double.parseDouble(responseData[0]);

        assertNotEquals(2.0, realValue, 0.0);

    }

    @Test
    void payTransaction_ValueNotNull() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponse = transactionService.fill(token,testValueToFill);
        APIResponse realApiResponsePay = transactionService.pay(token,testValueToWithdraw,testEmailTwo);
        String[] responseData = realApiResponsePay.getData().toString().split(" ");
        Double realValue =  Double.valueOf(responseData[0]);

        Assertions.assertNotNull(realValue);

    }

    @Test
    void payTransaction_DoesNotThrowException() {

        String token = CreateAndLogInTestUser();

        Assertions.assertDoesNotThrow(()-> {
            APIResponse realApiResponse = transactionService.fill(token,testValueToFill);
            APIResponse realApiResponsePay = transactionService.pay(token,testValueToWithdraw,testEmailTwo);
        });
    }

    @Test
    void payTransaction_StoredTransaction() {

        String token = CreateAndLogInTestUser();

        String testDate = getNewDate();

        APIResponse realApiResponse = transactionService.fill(token,testValueToFill);
        APIResponse realApiResponsePay = transactionService.pay(token,testValueToWithdraw,testEmailTwo);
        List<UserTransaction> verifyTransaction = userTransactionRepository.findAllByDateTimeBetweenAndEmail(
                testDate,LocalDateTime.now().toString(),testEmail);

        Assertions.assertNotNull(verifyTransaction);

    }

    @Test
    void payTransaction_StoredTransaction_ContentNotNull() {

        String token = CreateAndLogInTestUser();

        String testDate = getNewDate();

        APIResponse realApiResponse = transactionService.fill(token,testValueToFill);
        APIResponse realApiResponsePay = transactionService.pay(token,testValueToWithdraw,testEmailTwo);
        List<UserTransaction> verifyTransaction = userTransactionRepository.findAllByDateTimeBetweenAndEmail(
                testDate,LocalDateTime.now().toString(),testEmail);

        Assertions.assertNotNull(verifyTransaction);
        for (UserTransaction transaction: verifyTransaction
        ) {Assertions.assertNotNull(transaction);}

    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////        GET         ////////////////////////////////////

    @Test
    void getTransactionNotNull() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponseFill = transactionService.fill(token,testValueToFill);
        APITransactionsSumaryResponse realApiResponseGet =
                transactionService.getTransactions(token,startDate,endDate);

        Assertions.assertNotNull(realApiResponseGet);

    }

    @Test
    void getTransaction_InstanceOfObject() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponseFill = transactionService.fill(token,testValueToFill);
        APITransactionsSumaryResponse realApiResponseGet =
                transactionService.getTransactions(token,startDate,endDate);

        Assertions.assertInstanceOf(Object.class,realApiResponseGet);

    }

    @Test
    void getTransaction_Data_InstanceOfArrayList() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponseFill = transactionService.fill(token,testValueToFill);
        APITransactionsSumaryResponse realApiResponseGet =
                transactionService.getTransactions(token,startDate,endDate);

        Assertions.assertInstanceOf(ArrayList.class,realApiResponseGet.getTransactions());

    }

    @Test
    void getTransaction_Status_InstanceOfHttp() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponseFill = transactionService.fill(token,testValueToFill);
        APITransactionsSumaryResponse realApiResponseGet =
                transactionService.getTransactions(token,startDate,endDate);

        Assertions.assertInstanceOf(HttpStatus.class,realApiResponseGet.getStatus());

    }

    @Test
    void getTransaction_Error_InstanceOfNull_WhenPasses() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponseFill = transactionService.fill(token,testValueToFill);
        APITransactionsSumaryResponse realApiResponseGet =
                transactionService.getTransactions(token,startDate,endDate);

        Assertions.assertNull(realApiResponseGet.getError());

    }

    @Test
    void getTransaction_ValueNotNull() {

        String token = CreateAndLogInTestUser();

        APIResponse realApiResponse = transactionService.fill(token,testValueToFill);
        APITransactionsSumaryResponse realApiResponseGet =
                transactionService.getTransactions(token,startDate,endDate);

        Assertions.assertNotNull(realApiResponseGet.getTransactions());

    }

    @Test
    void getTransaction_ValueEmpty() {

        String emptyEndDate = "2022-07-27";
        String token = CreateAndLogInTestUser();

        APITransactionsSumaryResponse realApiResponseGet =
                transactionService.getTransactions(token,startDate,emptyEndDate);

        ArrayList<Object> myArrayTest= new ArrayList<>();

        Assertions.assertEquals(myArrayTest,realApiResponseGet.getTransactions());

    }

    @Test
    void getTransaction_DoesNotThrowException() {

        String token = CreateAndLogInTestUser();

        Assertions.assertDoesNotThrow(()-> {
            APITransactionsSumaryResponse realApiResponseGet =
                    transactionService.getTransactions(token,startDate,endDate);
        });
    }


}