package com.aevw.app.service.transaction;

import com.aevw.app.exception.ApiRequestException;
import com.aevw.app.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class TransactionServiceImplementationGetDatesMethodTest {

    @Autowired
    UserService userService;

    TransactionServiceImplementation transactionServiceImplementation = new TransactionServiceImplementation();

    @Test
    void getDatesNotNull(){

        String rawStartDate = "2021-04-12";
        String rawEndDate = "2022-08-31";

        ArrayList<Integer> testDates = transactionServiceImplementation.getDates(rawStartDate,rawEndDate);

        Assertions.assertNotNull(testDates);

    }

    @Test
    void getDatesInstanceOfArrayList(){
        String rawStartDate = "2021-04-12";
        String rawEndDate = "2022-08-31";

        ArrayList<Integer> testDates = transactionServiceImplementation.getDates(rawStartDate,rawEndDate);

        Assertions.assertInstanceOf(ArrayList.class,testDates);
    }

    @Test
    void getDatesArrayEquals(){

        String rawStartDate = "2021-04-12";
        String rawEndDate = "2022-08-31";

        Integer expectedStartYear = 2021;
        Integer expectedStartMonth = 4;
        Integer expectedStartDay = 12;

        Integer expectedEndYear = 2022;
        Integer expectedEndMonth = 8;
        Integer expectedEndDay = 31;

        ArrayList<Integer> expectedArray = new ArrayList<>();
        expectedArray.add(expectedStartYear);
        expectedArray.add(expectedStartMonth);
        expectedArray.add(expectedStartDay);
        expectedArray.add(expectedEndYear);
        expectedArray.add(expectedEndMonth);
        expectedArray.add(expectedEndDay);

        ArrayList<Integer> testDates = transactionServiceImplementation.getDates(rawStartDate,rawEndDate);

        Assertions.assertEquals(testDates,expectedArray);

    }

    @Test
    void getDatesStartYearEquals(){

        String rawStartDate = "2021-04-12";
        String rawEndDate = "2022-08-31";
        Integer expectedStartYear = 2021;

        ArrayList<Integer> testDates = transactionServiceImplementation.getDates(rawStartDate,rawEndDate);

        Assertions.assertEquals(testDates.get(0),expectedStartYear);

    }

    @Test
    void getDatesStartMonthEquals(){

        String rawStartDate = "2021-04-12";
        String rawEndDate = "2022-08-31";
        Integer expectedStartMonth = 4;

        ArrayList<Integer> testDates = transactionServiceImplementation.getDates(rawStartDate,rawEndDate);

        Assertions.assertEquals(testDates.get(1),expectedStartMonth);

    }

    @Test
    void getDatesStartDayEquals(){

        String rawStartDate = "2021-04-12";
        String rawEndDate = "2022-08-31";
        Integer expectedStartDay = 12;

        ArrayList<Integer> testDates = transactionServiceImplementation.getDates(rawStartDate,rawEndDate);
        Assertions.assertEquals(testDates.get(2),expectedStartDay);

    }

    @Test
    void getDatesEndYearEquals(){

        String rawStartDate = "2021-04-12";
        String rawEndDate = "2022-08-31";
        Integer expectedEndYear = 2022;

        ArrayList<Integer> testDates = transactionServiceImplementation.getDates(rawStartDate,rawEndDate);
        Assertions.assertEquals(testDates.get(3),expectedEndYear);

    }

    @Test
    void getDatesEndMonthEquals(){

        String rawStartDate = "2021-04-12";
        String rawEndDate = "2022-08-31";
        Integer expectedEndMonth = 8;

        ArrayList<Integer> testDates = transactionServiceImplementation.getDates(rawStartDate,rawEndDate);

        Assertions.assertEquals(testDates.get(4),expectedEndMonth);

    }

    @Test
    void getDatesEndDayEquals(){

        String rawStartDate = "2021-04-12";
        String rawEndDate = "2022-08-31";
        Integer expectedEndDay = 31;

        ArrayList<Integer> testDates = transactionServiceImplementation.getDates(rawStartDate,rawEndDate);
        Assertions.assertEquals(testDates.get(5),expectedEndDay);
    }

    @Test
    void getDatesThrowsAPIRequestException_InvalidData_PassingEmptyStrings(){

        String rawStartDate = "";
        String rawEndDate = "";

        Exception exception = Assertions.assertThrows(
                ApiRequestException.class,()-> transactionServiceImplementation.getDates(rawStartDate,rawEndDate)
        );
        String expectedMessage = "Invalid data, try again!";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getDatesThrowsAPIRequestException_InvalidData_PassingNull(){

        Exception exception = Assertions.assertThrows(
                ApiRequestException.class,()-> transactionServiceImplementation.getDates(null,null)
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
                ApiRequestException.class,()-> transactionServiceImplementation.getDates(null,null)
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
                ApiRequestException.class,()-> transactionServiceImplementation.getDates(null,null)
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
                    ApiRequestException.class,()-> transactionServiceImplementation.getDates(rawStartDate,rawEndDate)
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
                ApiRequestException.class,()-> transactionServiceImplementation.getDates(rawStartDate,rawEndDate)
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
                ApiRequestException.class,()-> transactionServiceImplementation.getDates(rawStartDate,rawEndDate)
        );
        String expectedMessage = "Invalid data, try again!";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void getDates_DoesNotThrow_APIRequestException(){

        String rawStartDate = "2021-04-12";
        String rawEndDate = "2022-08-31";

        Assertions.assertDoesNotThrow(()->{
            ArrayList<Integer> testDates = transactionServiceImplementation.getDates(rawStartDate,rawEndDate);});

    }

    @Test
    void getDates_InstanceOfInteger_StartDateYear(){

        String rawStartDate = "2021-04-12";
        String rawEndDate = "2022-08-31";

        ArrayList<Integer> testDates = transactionServiceImplementation.getDates(rawStartDate,rawEndDate);

        Assertions.assertInstanceOf(Integer.class, testDates.get(0));
    }

    @Test
    void getDates_InstanceOfInteger_StartDateMonth(){

        String rawStartDate = "2021-04-12";
        String rawEndDate = "2022-08-31";

        ArrayList<Integer> testDates = transactionServiceImplementation.getDates(rawStartDate,rawEndDate);

        Assertions.assertInstanceOf(Integer.class, testDates.get(1));
    }

    @Test
    void getDates_InstanceOfInteger_StartDateDay(){

        String rawStartDate = "2021-04-12";
        String rawEndDate = "2022-08-31";

        ArrayList<Integer> testDates = transactionServiceImplementation.getDates(rawStartDate,rawEndDate);

        Assertions.assertInstanceOf(Integer.class, testDates.get(2));
    }

    @Test
    void getDates_InstanceOfInteger_EndDateYear(){

        String rawStartDate = "2021-04-12";
        String rawEndDate = "2022-08-31";

        ArrayList<Integer> testDates = transactionServiceImplementation.getDates(rawStartDate,rawEndDate);

        Assertions.assertInstanceOf(Integer.class, testDates.get(3));
    }

    @Test
    void getDates_InstanceOfInteger_EndDateMonth(){

        String rawStartDate = "2021-04-12";
        String rawEndDate = "2022-08-31";

        ArrayList<Integer> testDates = transactionServiceImplementation.getDates(rawStartDate,rawEndDate);

        Assertions.assertInstanceOf(Integer.class, testDates.get(4));
    }

    @Test
    void getDates_InstanceOfInteger_EndDateDay(){

        String rawStartDate = "2021-04-12";
        String rawEndDate = "2022-08-31";

        ArrayList<Integer> testDates = transactionServiceImplementation.getDates(rawStartDate,rawEndDate);

        Assertions.assertInstanceOf(Integer.class, testDates.get(5));
    }

}