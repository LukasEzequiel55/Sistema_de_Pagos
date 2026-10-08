package com.payments.exceptions;

public class InsufficientFundsException extends SecurityException {
    public InsufficientFundsException(String message) {
        super(message);
    }//constructor
    //La excepción se lanza cuando la transaccion es superior al limite disponible
}//clase
