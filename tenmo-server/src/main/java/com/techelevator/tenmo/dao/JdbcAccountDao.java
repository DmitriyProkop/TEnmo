package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;
    private TransactionsDao transactionsDao;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate, TransactionsDao transactionsDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionsDao = transactionsDao;
    }


    @Override
    public List<Account> findAll(){
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT account_id, user_id, balance\n" +
                "FROM account;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            Account account = mapRowtoAccount(results);
            accounts.add(account);
        }

        return accounts;
    }

    @Override
    public Account findByUserId(Long userId) {
        Account account = null;

        String sql = "SELECT account_id, user_id, balance\n" +
                "FROM account\n" +
                "WHERe user_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        if(rowSet.next()){
            account = mapRowtoAccount(rowSet);
        }

        return account;
    }

    @Override
    public Account findByAccountId(Long accountId) {
        Account account = null;

        String sql = "SELECT account_id, user_id, balance\n" +
                "FROM account\n" +
                "WHERE account_id = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId);
        if(result.next()){
            account = mapRowtoAccount(result);
        }

        return account;
    }



    @Override
    public void addBalance(Long userId, int toAdd) {

        Account updatedAccount = null;


        String sql = "UPDATE account SET balance = balance + ?\n" +
                "WHERE user_id = ?;";

        try{

            int numRows = jdbcTemplate.update(sql, (double)toAdd, userId);

            if(numRows == 0) {
                throw new Exception("Zero rows affected, expected at least 1");
            } else {
                updatedAccount = findByUserId(userId);
            }

        } catch(Exception ex){
            System.out.println("Something went wrong");
        }

    }

    @Override
    public boolean create(int iBalance, Long userId){

        String sql = "INSERT INTO account(user_id, balance)\n" +
                "VALUES(?, ?)\n" +
                "RETURNING account_id;";
        Integer newAccountId;
        try{
            newAccountId = jdbcTemplate.queryForObject(sql, Integer.class, userId, iBalance);
        }catch(DataAccessException ex){
            return false;
        }


        return true;
    }

    @Override
    public int getBalance(Long id){

        int balance = 0;

        String sql = "SELECT balance\n" +
                "FROM account\n" +
                "WHERE user_id = ?;\n";

        try{

            balance = jdbcTemplate.queryForObject(sql, Integer.class, id);

        } catch (Exception ex){
            System.out.println("Oops");
        }

        return balance;
    }

    @Override
    public void subtractBalance(Long userId, int toSubtract){
        Account updatedAccount = null;


        String sql = "UPDATE account SET balance = balance - ?\n" +
                "WHERE user_id = ?;";

        try{

            int numRows = jdbcTemplate.update(sql, (double)toSubtract, userId);

            if(numRows == 0) {
                throw new Exception("Zero rows affected, expected at least 1");
            } else {
                updatedAccount = findByUserId(userId);
            }

        } catch(Exception ex){
            System.out.println("Something went wrong");
        }
    }


    @Override
    public void moneyTransfer(Long senderId, Long receiverId, int amtToTransfer){

        if(!senderId.equals(receiverId) && getBalance(senderId) >= amtToTransfer && amtToTransfer > 0) {
            subtractBalance(senderId, amtToTransfer);
            addBalance(receiverId, amtToTransfer);

            LocalDate transactionDate = LocalDate.now();

            transactionsDao.create(senderId, receiverId, amtToTransfer, transactionDate);
        }

    }


    private Account mapRowtoAccount(SqlRowSet result){
        Account account = new Account();
        account.setId(result.getLong("account_id"));
        account.setUser_id(result.getLong("user_id"));
        account.setBalance((int)result.getDouble("balance"));

        return account;
    }

}
