package com.aevw.app.repository;

import com.aevw.app.entity.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTransactionRepository extends JpaRepository<UserTransaction,String> {

    List<UserTransaction> findAllByDateTimeBetweenAndEmail(String start_date, String end_date, String email);

    UserTransaction findByEmail(String rawEmail);

    UserTransaction findByDateTime(String testDate);
}
