package com.payments.entities;

import com.payments.exceptions.InsufficientFundsException;
import com.payments.exceptions.InvalidPaymentException;

public class CreditCardPayment extends Payment {
    private String cardNumber;
    private String cardHolder;
    private double creditLimit;

    public CreditCardPayment(String id, double monto, String cardNumber, String cardHolder, double creditLimit) {
        super(id, monto);

        if (cardNumber == null || cardNumber.trim().isEmpty() ||
                cardHolder == null || cardHolder.trim().isEmpty() ||
                creditLimit < 0) {
            throw new InvalidPaymentException("Los datos de la tarjeta no son válidos.");
        }

        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.creditLimit = creditLimit;
    }

    @Override
    public void processPayment() {
        if (getMonto() <= creditLimit) {
            setEstado(PaymentStatus.APPROVED);
            creditLimit -= getMonto();
            System.out.printf("Pago con tarjeta de crédito aprobado por: $%.2f%n", getMonto());
        } else {
            setEstado(PaymentStatus.REJECTED);
            throw new InsufficientFundsException(
                    String.format("Fondos insuficientes. Límite disponible: $%.2f", creditLimit));
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

    @Override
    public String toString() {
        return "CreditCardPayment{" +
                "cardNumber='****" + (cardNumber.length() > 4 ? cardNumber.substring(cardNumber.length() - 4) : cardNumber) + '\'' +
                ", cardHolder='" + cardHolder + '\'' +
                ", creditLimit=" + creditLimit +
                ", " + super.toString() +
                '}';
    }
}
