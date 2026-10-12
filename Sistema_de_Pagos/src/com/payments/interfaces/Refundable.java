package com.payments.interfaces;
import com.payments.exceptions.InvalidPaymentException;

public interface Refundable {
     //Reembolsa la totalidad del pago procesado.
    void refund() throws InvalidPaymentException;

    //Reembolsa un monto parcial del pago procesado.
    void refund(double amount) throws InvalidPaymentException;
}
