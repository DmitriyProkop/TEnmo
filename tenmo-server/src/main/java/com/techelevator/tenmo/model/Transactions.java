package com.techelevator.tenmo.model;

import java.time.LocalDate;

public class Transactions {

    private Long transactionId;
    private Long senderId;
    private Long receiverId;
    private double transactionAmount;
    private LocalDate transactionDate;

    private String transactionStatus;

    public Transactions(Long transactionId, Long senderId, Long receiverId, double transactionAmount, LocalDate transactionDate, String transactionStatus) {
        this.transactionId = transactionId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
        this.transactionStatus = transactionStatus;
    }

    public Transactions() {

    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionStatus(){
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
