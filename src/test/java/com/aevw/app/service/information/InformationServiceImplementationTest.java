package com.aevw.app.service.information;

import com.aevw.app.api.response.APIResponse;
import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.dto.information.InformationInputDTO;
import com.aevw.app.exception.ApiRequestException;
import com.aevw.app.service.user.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

class InformationServiceImplementationTest {

    @Autowired
    UserService userService;

    InformationServiceImplementation information = new InformationServiceImplementation();

    String test_start_date = "2021-04-12";
    String test_end_date = "2022-08-31";

    Integer expectedStartYear = 2021;
    Integer expectedStartMonth = 4;
    Integer expectedStartDay = 12;

    Integer expectedEndYear = 2022;
    Integer expectedEndMonth = 8;
    Integer expectedEndDay = 31;

    String rawInvalidStartDate = "";
    String rawInvalidEndDate = "";


    private AppUser CreateTestUser(){
        AppUser testUser = new AppUser();
        testUser.setCapital(100.0);
        return testUser;
    }

    private InformationInputDTO CreateInformationInputDTO(@RequestParam(defaultValue = "USD", required = false) String currency){
        InformationInputDTO input = new InformationInputDTO();
        input.setCurrency(currency);
        return input;
    }

    private InformationInputDTO CreateInformationInputDTO(){
        InformationInputDTO input = new InformationInputDTO();
        input.setCurrency("USD");
        return input;
    }


    @Test
    void getDatesNotNull() {

        ArrayList<Integer> testGetDates = information.getDates(test_start_date,test_end_date);

        Assertions.assertNotNull(testGetDates);
    }

    @Test
    void getDatesSize() {

        ArrayList<Integer> testGetDates = information.getDates(test_start_date,test_end_date);

        Assertions.assertEquals(6, testGetDates.size());
    }

    @Test
    void getDates() {

        ArrayList<Integer> testGetDates = information.getDates(test_start_date,test_end_date);

        Assertions.assertEquals(6, testGetDates.size());
    }

    @Test
    void getDatesArrayEquals() {

        ArrayList<Integer> expectedArray = new ArrayList<>();
        expectedArray.add(expectedStartYear);
        expectedArray.add(expectedStartMonth);
        expectedArray.add(expectedStartDay);
        expectedArray.add(expectedEndYear);
        expectedArray.add(expectedEndMonth);
        expectedArray.add(expectedEndDay);

        ArrayList<Integer> testDates = information.getDates(test_start_date, test_end_date);

        Assertions.assertEquals(testDates, expectedArray);

    }

    @Test
    void getDatesStartYearEquals(){

        ArrayList<Integer> testDates = information.getDates(test_start_date,test_end_date);

        Assertions.assertEquals(testDates.get(0),expectedStartYear);

    }

    @Test
    void getDatesStartMonthEquals(){

        ArrayList<Integer> testDates = information.getDates(test_start_date,test_end_date);

        Assertions.assertEquals(testDates.get(1),expectedStartMonth);

    }

    @Test
    void getDatesStartDayEquals(){
        ArrayList<Integer> testDates = information.getDates(test_start_date,test_end_date);
        Assertions.assertEquals(testDates.get(2),expectedStartDay);
    }

    @Test
    void getDatesEndYearEquals(){

        ArrayList<Integer> testDates = information.getDates(test_start_date,test_end_date);
        Assertions.assertEquals(testDates.get(3),expectedEndYear);
    }


    @Test
    void getDatesEndMonthEquals(){

        ArrayList<Integer> testDates = information.getDates(test_start_date,test_end_date);

        Assertions.assertEquals(testDates.get(4),expectedEndMonth);

    }

    @Test
    void getDatesEndDayEquals(){

        ArrayList<Integer> testDates = information.getDates(test_start_date,test_end_date);
        Assertions.assertEquals(testDates.get(5),expectedEndDay);
    }

    @Test
    void getDatesThrowsAPIRequestException_InvalidData_PassingEmptyStrings(){

        Exception exception = Assertions.assertThrows(
                ApiRequestException.class,()-> information.getDates(rawInvalidStartDate,rawInvalidEndDate)
        );
        String expectedMessage = "Invalid data, try again!";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getDatesThrowsAPIRequestException_InvalidData_PassingNull(){

        Exception exception = Assertions.assertThrows(
                ApiRequestException.class,()-> information.getDates(null,null)
        );
        String expectedMessage = "Invalid data, try again!";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getDatesThrowsAPIRequestException_InvalidData_PassingStringWithExtraSlashes(){

        String rawStartDate = "2021-04--12";
        String rawEndDate = "2022-08-31";

        Exception exception = Assertions.assertThrows(
                ApiRequestException.class,()-> information.getDates(rawStartDate,rawEndDate)
        );
        String expectedMessage = "Invalid data, try again!";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getDatesThrowsAPIRequestException_InvalidData_PassingStringWithoutSlashes(){

        String rawStartDate = "20210412";
        String rawEndDate = "20220831";

        Exception exception = Assertions.assertThrows(
                ApiRequestException.class,()-> information.getDates(rawStartDate,rawEndDate)
        );
        String expectedMessage = "Invalid data, try again!";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getDatesThrowsAPIRequestException_InvalidData_OnlyPassingYears(){

        String rawStartDate = "2021";
        String rawEndDate = "2022";

        Exception exception = Assertions.assertThrows(
                ApiRequestException.class,()-> information.getDates(rawStartDate,rawEndDate)
        );
        String expectedMessage = "Invalid data, try again!";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getDatesThrowsAPIRequestException_InvalidData_OnlyPassingMonths(){

        String rawStartDate = "05";
        String rawEndDate = "02";

        Exception exception = Assertions.assertThrows(
                ApiRequestException.class,()-> information.getDates(rawStartDate,rawEndDate)
        );
        String expectedMessage = "Invalid data, try again!";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getDatesThrowsAPIRequestException_InvalidData_OnlyPassingYearsAndMonths(){

        String rawStartDate = "2021-05";
        String rawEndDate = "2022-02";

        Exception exception = Assertions.assertThrows(
                ApiRequestException.class,()-> information.getDates(rawStartDate,rawEndDate)
        );
        String expectedMessage = "Invalid data, try again!";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));

    }


    @Test
    void getDates_DoesNotThrow_APIRequestException(){

        Assertions.assertDoesNotThrow(()->{
            ArrayList<Integer> testDates = information.getDates(test_start_date,test_end_date);
        System.out.println(testDates);});


    }

    @Test
    void getDates_InstanceOfInteger_StartDateYear(){

        ArrayList<Integer> testDates = information.getDates(test_start_date,test_end_date);

        Assertions.assertInstanceOf(Integer.class, testDates.get(0));
    }

    @Test
    void getDates_InstanceOfInteger_StartDateMonth(){

        ArrayList<Integer> testDates = information.getDates(test_start_date,test_end_date);

        Assertions.assertInstanceOf(Integer.class, testDates.get(1));
    }



    @Test
    void balanceResponse_NotNull() {

        AppUser testUser = CreateTestUser();
        InformationInputDTO balance = CreateInformationInputDTO();
        APIResponse realResponse = information.balance(balance, testUser);

        Assertions.assertNotNull(realResponse);
    }

    @Test
    void balanceResponse_DataNotNull() {

        AppUser testUser = CreateTestUser();
        InformationInputDTO balance = CreateInformationInputDTO();
        APIResponse realResponse = information.balance(balance, testUser);

        Assertions.assertNotNull(realResponse.getData());
    }

    @Test
    void balanceResponse_StatusNotNull() {

        AppUser testUser = CreateTestUser();
        InformationInputDTO balance = CreateInformationInputDTO();
        APIResponse realResponse = information.balance(balance, testUser);

        Assertions.assertNotNull(realResponse.getStatus());
    }

    @Test
    void balanceResponse_ErrorNull() {

        AppUser testUser = CreateTestUser();
        InformationInputDTO balance = CreateInformationInputDTO();
        APIResponse realResponse = information.balance(balance, testUser);

        Assertions.assertNull(realResponse.getError());
    }

    @Test
    void balanceResponse_DataTrue() {

        AppUser testUser = CreateTestUser();
        InformationInputDTO balance = CreateInformationInputDTO();
        APIResponse realResponse = information.balance(balance, testUser);

        Assertions.assertTrue((realResponse.getData().toString().contains("100.0")));
    }

    @Test
    void balanceResponse_DataFalse() {

        AppUser testUser = CreateTestUser();
        InformationInputDTO balance = CreateInformationInputDTO();
        APIResponse realResponse = information.balance(balance, testUser);

        Assertions.assertFalse((realResponse.getData().toString().contains("1000")));
    }

    @Test
    void balanceResponse_StatusOk() {

        AppUser testUser = CreateTestUser();
        InformationInputDTO balance = CreateInformationInputDTO();
        APIResponse realResponse = information.balance(balance, testUser);

        Assertions.assertEquals(realResponse.getStatus(), HttpStatus.OK);
    }

    @Test
    void balanceResponse_NotStatusBadRequest() {

        AppUser testUser = CreateTestUser();
        InformationInputDTO balance = CreateInformationInputDTO();
        APIResponse realResponse = information.balance(balance, testUser);

        Assertions.assertNotEquals(realResponse.getStatus(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void balanceResponse_NotStatusBadRequestEUR() {

        AppUser testUser = CreateTestUser();

        InformationInputDTO balance = CreateInformationInputDTO("EUR");
        APIResponse realResponse = information.balance(balance, testUser);


        Assertions.assertNotEquals(realResponse.getStatus(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void balanceResponse_NotStatusBadRequestMXN() {

        AppUser testUser = CreateTestUser();
        InformationInputDTO balance = CreateInformationInputDTO("MXN");
        APIResponse realResponse = information.balance(balance, testUser);

        Assertions.assertNotEquals(realResponse.getStatus(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void balanceResponse_NotStatusBadRequestGBP() {

        AppUser testUser = CreateTestUser();
        InformationInputDTO balance = CreateInformationInputDTO("GBP");
        APIResponse realResponse = information.balance(balance, testUser);

        Assertions.assertNotEquals(realResponse.getStatus(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void balanceResponse_DataNotNullGBP() {

        AppUser testUser = CreateTestUser();
        InformationInputDTO balance = CreateInformationInputDTO("GBP");
        APIResponse realResponse = information.balance(balance, testUser);

        Assertions.assertNotNull(realResponse.getData());
    }

    @Test
    void balanceResponse_DataNotNullEUR() {

        AppUser testUser = CreateTestUser();
        InformationInputDTO balance = CreateInformationInputDTO("EUR");
        APIResponse realResponse = information.balance(balance, testUser);

        Assertions.assertNotNull(realResponse.getData());
    }

    @Test
    void balanceResponse_DataNotNullMXN() {

        AppUser testUser = CreateTestUser();
        InformationInputDTO balance = CreateInformationInputDTO("MXN");
        APIResponse realResponse = information.balance(balance, testUser);

        Assertions.assertNotNull(realResponse.getData());
    }


    @Test
    void summary() {
    }

    @Test
    void series() {
    }
}