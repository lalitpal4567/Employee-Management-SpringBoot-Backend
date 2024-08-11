package com.vserviceu.employee.customException;

public class EmailAlreadyExistedException extends RuntimeException{
    public EmailAlreadyExistedException(){}
    public EmailAlreadyExistedException(String message){
        super(message);
    }
}
