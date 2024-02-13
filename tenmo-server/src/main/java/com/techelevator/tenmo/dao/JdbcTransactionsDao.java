package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transactions;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransactionsDao implements TransactionsDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcTransactionsDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }





    @Override
    public List<Transactions> findByUserId(Long userId) {
        List<Transactions> transactions = new ArrayList<>();
        String sql = "SELECT transaction_id, sender_id, receiver_id, transaction_amount, transaction_date\n" +
                "FROM transactions\n" +
                "WHERE sender_id = ? OR receiver_id = ?;" +
                "ORDER BY transaction_date DESC";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
        while(results.next()){
            Transactions transaction = mapRowtoTransactions(results);
            transactions.add(transaction);
        }

        return transactions;
    }

    @Override
    public Transactions findByTransactionId(Long transactionId) {
        Transactions transaction = null;

        String sql = "SELECT transaction_id, sender_id, receiver_id, transaction_amount, transaction_date\n" +
                "FROM transactions\n" +
                "WHERE transaction_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transactionId);

        if(results.next()){
            transaction = mapRowtoTransactions(results);
        }

        return transaction;
    }

    @Override
    public boolean create(Long senderId, Long receiverId, double transactionAmount, LocalDate transactionDate) {
        String sql ="INSERT INTO transactions (sender_id, receiver_id, transaction_amount, transaction_date)\n" +
                "VALUES(?, ?, ?, ?) RETURNING transaction_id;";
        Integer newTransactionId;
        try{
            newTransactionId = jdbcTemplate.queryForObject(sql, Integer.class, senderId, receiverId, transactionAmount,
                    transactionDate);
        } catch (DataAccessException e){
            return false;
        }

        return true;
    }

    @Override
    public Transactions findByTransactionStatus(String transactionStatus) {
        Transactions transactions = null;

        String sql = "SELECT sender_id, receiver_id, transaction_amount, transaction_date, transaction_status\n" +
                "FROM transactions\n" +
                "WHERE transaction_status ILIKE '?';";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transactionStatus);

        if(results.next()){
            transactions = mapRowtoTransactions(results);
        }

        return transactions;
    }

    private Transactions mapRowtoTransactions(SqlRowSet results){
        Transactions transactions = new Transactions();
        transactions.setTransactionId(results.getLong("transaction_id"));
        transactions.setTransactionAmount(results.getDouble("transaction_amount"));
        transactions.setSenderId(results.getLong("sender_id"));
        transactions.setReceiverId(results.getLong("receiver_id"));
        transactions.setTransactionStatus(results.getString("transaction_status"));


        if(results.getDate("transaction_date") != null) {
            transactions.setTransactionDate(results.getDate("transaction_date").toLocalDate());
        }

        return transactions;
    }
}
