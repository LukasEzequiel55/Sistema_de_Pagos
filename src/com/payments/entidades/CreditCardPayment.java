package com.payments.entities;

public class CreditCardPayment extends Payment {
    private String cardNumber;
    private String cardHolder;
    private double creditLimit;

    public CreditCardPayment(String id, double monto, String cardNumber, String cardHolder, double creditLimit) {
        super(id, monto);
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.creditLimit = creditLimit;
    }

    @Override
    public void processPayment() throws Exception {
        if (getMonto() <= creditLimit) {
            setEstado(PaymentStatus.APPROVED);
            creditLimit -= getMonto();
            System.out.println("Pago con tarjeta de crédito aprobado por: $" + getMonto());
        } else {
            setEstado(PaymentStatus.REJECTED);
            throw new Exception("Fondos insuficientes. Límite disponible: $" + creditLimit);
        }
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public double getCreditLimit() {
        return creditLimit;
    }
}
