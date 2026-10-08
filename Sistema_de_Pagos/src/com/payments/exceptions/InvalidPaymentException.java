package com.payments.exceptions;

public class InvalidPaymentException extends SecurityException {
    public InvalidPaymentException(String message) {
        super(message);
    }//constructor
    //La excepción se lanza cuando los datos de un pago, tarjeta, correo
    // no cumplen con las validaciones
}//clase
