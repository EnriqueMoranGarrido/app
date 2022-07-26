package com.aevw.app.repository;

import com.aevw.app.entity.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTransactionRepository extends JpaRepository<UserTransaction,String> {

    UserTransaction findAllByDateTimeBetweenAndEmail(String start_date, String end_date,String email);
}
