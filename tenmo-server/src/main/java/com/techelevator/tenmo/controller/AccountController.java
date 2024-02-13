package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransactionsDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.http.HttpResponse;

@RestController
public class AccountController {

    @Autowired
    private AccountDao accountDao;



    @RequestMapping(path="/balance/{id}")
    public int checkBalance(@PathVariable Long id){

        return accountDao.getBalance(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(path="/transfer", method = RequestMethod.PUT)

    public void moneyTransfer(@RequestParam Long senderId, @RequestParam Long receiverId,
                              @RequestParam int amtToTransfer){


        accountDao.moneyTransfer(senderId, receiverId, amtToTransfer);
    }



}
