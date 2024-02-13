package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transactions;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;



public interface TransactionsDao {

    List<Transactions> findByUserId(Long userId);

    Transactions findByTransactionId(Long transactionId);

    boolean create(Long senderId, Long receiverId, double transactionAmount, LocalDate transactionDate);

    Transactions findByTransactionStatus(String transactionStatus);

}
