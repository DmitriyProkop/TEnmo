package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.TransactionsDao;
import com.techelevator.tenmo.model.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionsController {

    @Autowired
    private TransactionsDao transactionsDao;

    @RequestMapping(path="/transactions", method = RequestMethod.GET)
    public List<Transactions> listTransactions(@RequestParam Long userId){


        return transactionsDao.findByUserId(userId);

    }

    @RequestMapping(path="/transactions/{id}", method = RequestMethod.GET)
    public Transactions showTransactionById(@PathVariable Long id){
        return transactionsDao.findByTransactionId(id);
    }

    @RequestMapping(path = "/paymentrequest", method = RequestMethod.GET)
    public Transactions showTransactionStatus(@RequestParam String transactionStatus){
        return transactionsDao.findByTransactionStatus(transactionStatus);
    }
}
