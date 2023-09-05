package com.nagarro.assignment2.exceptions;

public class CustomException extends Exception {

    private String msg;

    public CustomException(String msg) {
        this.msg = msg;
    }
    

}
