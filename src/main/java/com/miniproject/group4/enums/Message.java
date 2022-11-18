package com.miniproject.group4.enums;

public enum Message {
    PAYROLL_NOT_FOUND("Payroll %s is not found!"),
    PAYROLL_DELETE("Payroll %s has been deleted."),
    USER_NOT_FOUND("User %s is not found!"),
    USER_DELETE("User %s has been deleted."),
    ACCESS_DENIED("User %s has unauthorized access for the %s method with endpoint of %s"),
    INVALID_USER("Invalid User");

    private String message;
    Message(final String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
