package com.miniproject.group4.enums;

public enum Message {
    PAYROLL_NOT_FOUND("Payroll not found!"),
    PAYROLL_DELETE("Payroll %s has been deleted."),
    USER("User not found!"),
    USER_DELETE("User %s has been deleted."),
    INVALID_USER("Invalid User");

    private String message;
    Message(final String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
