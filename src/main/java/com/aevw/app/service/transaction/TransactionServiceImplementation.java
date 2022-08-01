package com.aevw.app.service.transaction;

import com.aevw.app.api.response.APIResponse;
import com.aevw.app.api.response.APITransactionsSumaryResponse;
import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.dto.transaction.TransactionsDTO;
import com.aevw.app.entity.UserTransaction;
import com.aevw.app.exception.ApiRequestException;
import com.aevw.app.repository.UserRepository;
import com.aevw.app.repository.UserTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@Slf4j
public class TransactionServiceImplementation implements TransactionService{

    @Autowired private UserRepository userRepository;
    @Autowired private UserTransactionRepository userTransactionRepository;


    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////        CREATE TRANSACTION         ////////////////////////////

    public void createTransaction(String email, Double value, String type){
        try{
            UserTransaction transaction = new UserTransaction(
                email,
                value,
                LocalDateTime.now().toString(),
                type);

            userTransactionRepository.save(transaction);

        }catch(Exception e){
            throw new ApiRequestException("Invalid data, try again");
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////              GET DATES              ///////////////////////////

    public ArrayList<Integer> getDates(String start_date, String end_date){

        // Try converting the Strings into Integers
        try{

        // Creating variables start and end from incoming data
        String[] start = start_date.split("-");
        String[] end = end_date.split("-");

        // Getting the value of the strings as integers
        int start_year = Integer.parseInt(start[0]);
        int start_month = Integer.parseInt(start[1]);
        int start_day = Integer.parseInt(start[2]);

        int end_year = Integer.parseInt(end[0]);
        int end_month = Integer.parseInt(end[1]);
        int end_day = Integer.parseInt(end[2]);

        // Creating an array to return the values
        ArrayList<Integer> returningArray = new ArrayList<>();

        // Filling the array with the values obtained from the parsed
        returningArray.add(start_year);
        returningArray.add(start_month);
        returningArray.add(start_day);
        returningArray.add(end_year);
        returningArray.add(end_month);
        returningArray.add(end_day);

        // return the array
        return returningArray;
        }
        catch (Exception e){
            // Throw exception
            throw  new ApiRequestException("Invalid data, try again!");
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////        FILL         ////////////////////////////////////
    @Override
    public APIResponse fill(AppUser fillUser, Double value) throws InterruptedException {

        // Create new API Response
        APIResponse apiResponse = new APIResponse();

        // Set the capital of the user
        fillUser.setCapital(fillUser.getCapital()+value);

        createTransaction(fillUser.getEmail(),value,"payment_fill");

        TimeUnit.SECONDS.sleep(1);

        // Set the api response data
        apiResponse.setData(value + " were added to " + fillUser.getEmail()
                                                      + " . Total capital: " + fillUser.getCapital());
        // return the api response
        return apiResponse;
    }


    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////        WITHDRAW         //////////////////////////////////
    @Override
    public APIResponse withdraw(AppUser withdrawUser, Double value) throws InterruptedException {

        // Create new API Response
        APIResponse apiResponse = new APIResponse();

        // If the user's capital is greater than the withdrawal requested:
            if(withdrawUser.getCapital() >= value){

                // Create withdrawn transaction
                createTransaction(withdrawUser.getEmail(),value,"payment_withdraw");

                TimeUnit.SECONDS.sleep(1);

                // Set the capital of the user
                withdrawUser.setCapital(withdrawUser.getCapital()-value);

                // Set the api response data
                apiResponse.setData(value + " were withdrawn to " + withdrawUser.getEmail()
                        + " . Total capital: " + withdrawUser.getCapital());
            }
        // return the api response
        return apiResponse;
    }


    //////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////        PAY         ////////////////////////////////////
    @Override
    public APIResponse pay(AppUser userPay, Double value, String email) throws InterruptedException {

        // Create new API Response
        APIResponse apiResponse = new APIResponse();

        // if the capital of the user making the payment is greater than the payment value:
            if(userPay.getCapital() >= value){

                // Finding the user that will receive the payment
                AppUser userBeingPaid = userRepository.findByEmail(email);

                // Verify if the user being paid exists, and it's not the same user making the payment.
                if(userBeingPaid != null && !(userBeingPaid.getEmail().equals(userPay.getEmail()))){

                    // Set the capital of the user making the payment
                    userPay.setCapital(userPay.getCapital()-value);

                    // Creating a new transaction for the user making the payment
                    createTransaction(userPay.getEmail(),value,"payment_made");

                    TimeUnit.SECONDS.sleep(1);

                    // Set the capital of the user receiving the payment
                    userBeingPaid.setCapital(userBeingPaid.getCapital()+value);

                    // Creating a new transaction for the user receiving the payment
                    createTransaction(userBeingPaid.getEmail(),value,"payment_received");

                    TimeUnit.SECONDS.sleep(1);

                    // Set the api response data
                    apiResponse.setData(value + " were paid from " + userPay.getEmail()
                            + " to " + userBeingPaid.getEmail()+ ". Total capital: " + userPay.getCapital());

                }
            }
        return apiResponse;
    }


    //////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////        GET         ////////////////////////////////////
    @Override
    public APITransactionsSumaryResponse getTransactions(AppUser userSummary, String start_date, String end_date) {

        // Transform the start and end dates from Strings to Integers
        ArrayList<Integer> dates = getDates(start_date,end_date);

        // Create new API Response
        APITransactionsSumaryResponse transactionResponse = new APITransactionsSumaryResponse();

        // Get the transactions between the provided dates for this user
        List<UserTransaction> transactions = userTransactionRepository.findAllByDateTimeBetweenAndEmail(
                LocalDateTime.of(dates.get(0),dates.get(1),dates.get(2),0,0,0) .toString(),
                LocalDateTime.of(dates.get(3),dates.get(4),dates.get(5),23,59,59) .toString(),
                userSummary.getEmail());

        ArrayList<TransactionsDTO> myResponseTransactions = new ArrayList<>();

        for (UserTransaction transaction: transactions
        ) {
            TransactionsDTO transactionsDTO = new TransactionsDTO(
                    transaction.getDateTime(),
                    transaction.getType(),
                    transaction.getMoney()
            );
            myResponseTransactions.add(transactionsDTO);
        }

        // Set the api response
        transactionResponse.setTransactions(myResponseTransactions);

        return transactionResponse;
    }
}
