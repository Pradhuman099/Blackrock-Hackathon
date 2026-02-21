package com.example.demo.Model;

public class InvalidTransaction extends Transaction {

    private String message;

    public InvalidTransaction(Transaction tx, String message) {
        super(tx.getDate(), tx.getAmount(), tx.getCeiling(), tx.getRemanent());
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
