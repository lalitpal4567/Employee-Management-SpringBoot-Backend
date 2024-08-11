package com.vserviceu.employee.customException;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(){}

    public EmployeeNotFoundException(String message){
        super(message);
    }
}
