package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {

    List<Account> findAll();

    Account findByUserId(Long userId);

    Account findByAccountId(Long accountId);

    void addBalance(Long userId, int toAdd);

    boolean create(int iBalance, Long userId);

    int getBalance(Long id);

    void subtractBalance(Long userId, int toSubtract);

    void moneyTransfer(Long senderId, Long receiverId, int amtToTransfer);

}
