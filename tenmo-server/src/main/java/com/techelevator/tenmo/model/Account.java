package com.techelevator.tenmo.model;

import com.techelevator.tenmo.dao.UserDao;

public class Account {

    public Account() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user.getId();
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private Long id;
    private Long user_id;
    private int balance;

    User user = new User();
    public Account(Long id, Long user_id, int balance) {
        this.id = id;
        this.user_id = user.getId();
        this.balance = balance;
    }


}
