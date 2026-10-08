package com.payments.interfaces;
import com.payments.exceptions.InvalidPaymentException;

public interface refundable {
    void refund() throws InvalidPaymentException;
    void refund(double amount) throws InvalidPaymentException;
}
