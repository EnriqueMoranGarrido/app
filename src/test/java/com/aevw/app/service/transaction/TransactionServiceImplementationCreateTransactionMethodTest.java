package com.aevw.app.service.transaction;

import com.aevw.app.entity.UserTransaction;
import com.aevw.app.repository.UserTransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransactionServiceImplementationCreateTransactionMethodTest {

    @Autowired TransactionServiceImplementation transactionServiceImplementation;
    @Autowired UserTransactionRepository userTransactionRepository;

    @Test
    void createTransactionNotNull(){

        String rawEmail ="testMail@gmail.com";
        Double rawValue = 10.0;
        String rawType = "Test_Type";

        transactionServiceImplementation.createTransaction(rawEmail,rawValue,rawType);
        UserTransaction userTransactionTest = userTransactionRepository.findByEmail(rawEmail);

        Assertions.assertNotNull(userTransactionTest);

    }

    @Test
    void createTransactionInstanceOfUserTransaction(){

        String rawEmail ="testMail1@gmail.com";
        Double rawValue = 10.0;
        String rawType = "Test_Type";

        transactionServiceImplementation.createTransaction(rawEmail,rawValue,rawType);
        UserTransaction userTransactionTest = userTransactionRepository.findByEmail(rawEmail);

        Assertions.assertInstanceOf(UserTransaction.class,userTransactionTest);
    }


    @Test
    void createTransactionEmailInstanceOfString(){
        String rawEmail ="testMail2@gmail.com";
        Double rawValue = 10.0;
        String rawType = "Test_Type";

        transactionServiceImplementation.createTransaction(rawEmail,rawValue,rawType);
        UserTransaction userTransactionTest = userTransactionRepository.findByEmail(rawEmail);

        Assertions.assertInstanceOf(String.class,userTransactionTest.getEmail());
    }

    @Test
    void createTransactionTypeInstanceOfString(){
        String rawEmail ="testMail3@gmail.com";
        Double rawValue = 10.0;
        String rawType = "Test_Type";

        transactionServiceImplementation.createTransaction(rawEmail,rawValue,rawType);
        UserTransaction userTransactionTest = userTransactionRepository.findByEmail(rawEmail);

        Assertions.assertInstanceOf(String.class,userTransactionTest.getType());
    }

    @Test
    void createTransactionValueInstanceOfDouble(){
        String rawEmail ="testMail4@gmail.com";
        Double rawValue = 10.0;
        String rawType = "Test_Type";

        transactionServiceImplementation.createTransaction(rawEmail,rawValue,rawType);
        UserTransaction userTransactionTest = userTransactionRepository.findByEmail(rawEmail);

        Assertions.assertInstanceOf(Double.class,userTransactionTest.getMoney());
    }

    @Test
    void createTransactionEqualsEmail(){
        String rawEmail ="testMail5@gmail.com";
        Double rawValue = 10.0;
        String rawType = "Test_Type";

        transactionServiceImplementation.createTransaction(rawEmail,rawValue,rawType);
        UserTransaction userTransactionTest = userTransactionRepository.findByEmail(rawEmail);

        Assertions.assertEquals(userTransactionTest.getEmail(),rawEmail);
    }



    @Test
    void createTransactionEqualsType(){
        String rawEmail ="testMail6@gmail.com";
        Double rawValue = 10.0;
        String rawType = "Test_Type";

        transactionServiceImplementation.createTransaction(rawEmail,rawValue,rawType);
        UserTransaction userTransactionTest = userTransactionRepository.findByEmail(rawEmail);

        Assertions.assertEquals(userTransactionTest.getType(),rawType);
    }



    @Test
    void createTransactionEqualsValue(){
        String rawEmail ="testMail7@gmail.com";
        Double rawValue = 10.0;
        String rawType = "Test_Type";

        transactionServiceImplementation.createTransaction(rawEmail,rawValue,rawType);
        UserTransaction userTransactionTest = userTransactionRepository.findByEmail(rawEmail);

        Assertions.assertEquals(userTransactionTest.getMoney(),rawValue);
    }

    @Test
    void createTransactionEmailNotNull(){
        String rawEmail ="testMail8@gmail.com";
        Double rawValue = 10.0;
        String rawType = "Test_Type";

        transactionServiceImplementation.createTransaction(rawEmail,rawValue,rawType);
        UserTransaction userTransactionTest = userTransactionRepository.findByEmail(rawEmail);

        Assertions.assertNotNull(userTransactionTest.getEmail());
    }

    @Test
    void createTransactionTypeNotNull(){
        String rawEmail ="testMail9@gmail.com";
        Double rawValue = 10.0;
        String rawType = "Test_Type";

        transactionServiceImplementation.createTransaction(rawEmail,rawValue,rawType);
        UserTransaction userTransactionTest = userTransactionRepository.findByEmail(rawEmail);

        Assertions.assertNotNull(userTransactionTest.getType());
    }

    @Test
    void createTransactionValueNotNull(){
        String rawEmail ="testMail10@gmail.com";
        Double rawValue = 10.0;
        String rawType = "Test_Type";

        transactionServiceImplementation.createTransaction(rawEmail,rawValue,rawType);
        UserTransaction userTransactionTest = userTransactionRepository.findByEmail(rawEmail);

        Assertions.assertNotNull(userTransactionTest.getMoney());
    }

    @Test
    void createTransactionValueNotSame(){
        String rawEmail ="testMail11@gmail.com";
        Double rawValue = 10.0;
        String rawType = "Test_Type";
        Double unexpected = 5.0;

        transactionServiceImplementation.createTransaction(rawEmail,rawValue,rawType);
        UserTransaction userTransactionTest = userTransactionRepository.findByEmail(rawEmail);

        Assertions.assertNotSame(unexpected,userTransactionTest.getMoney());
    }



}