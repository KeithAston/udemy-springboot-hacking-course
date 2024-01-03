package com.ka.udemyspringboothackingcourse.exceptions;

public class BadCredentialsException extends Exception{
    public BadCredentialsException(String errorMessage){
        super(errorMessage);
    }
}
