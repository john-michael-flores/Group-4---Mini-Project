package com.miniproject.group4.enums;

public enum Message {
    PAYROLL("Payroll not found!"),
    INVALID_USER("Invalid User");

    private final String message;
    Message(final String message){
        this.message = message;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
