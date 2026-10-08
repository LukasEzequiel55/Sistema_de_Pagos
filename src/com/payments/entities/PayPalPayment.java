package com.payments.entities;

import com.payments.exceptions.InsufficientFundsException;
import com.payments.exceptions.InvalidPaymentException;
import com.payments.interfaces.Refundable;

public class PayPalPayment extends Payment implements Refundable {

    private String email;
    private double saldoPayPal;

    public PayPalPayment(String id, double monto, String email, double saldoPayPal) {
        super(id, monto);

        if (!validarEmailPayPal(email) || saldoPayPal < 0) {
            throw new InvalidPaymentException("Los datos de PayPal no son válidos.");
        }

        this.email = email;
        this.saldoPayPal = saldoPayPal;
    }

    public boolean validarEmailPayPal(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email != null && email.matches(regex);
    }

    public boolean validarEmailPayPal() {
        return validarEmailPayPal(email);
    }

    public boolean procesarPagoPay(double montoPagar) {
        if (!validarEmailPayPal()) {
            setEstado(PaymentStatus.REJECTED);
            throw new InvalidPaymentException("Correo electrónico inválido.");
        }

        if (montoPagar <= 0) {
            setEstado(PaymentStatus.REJECTED);
            throw new InvalidPaymentException("Monto inválido.");
        }

        if (saldoPayPal < montoPagar) {
            setEstado(PaymentStatus.REJECTED);
            throw new InsufficientFundsException(
                    String.format("Saldo insuficiente. Saldo disponible: $%.2f | Requerido: $%.2f", saldoPayPal, montoPagar));
        }

        saldoPayPal -= montoPagar;
        setEstado(PaymentStatus.APPROVED);
        System.out.printf("Pago PayPal aprobado por: $%.2f%n", montoPagar);
        return true;
    }

    @Override
    public void processPayment() {
        procesarPagoPay(getMonto());
    }

    @Override
    public void refund() throws InvalidPaymentException {
        refund(getMonto());
    }

    @Override
    public void refund(double amount) throws InvalidPaymentException {
        if (getEstado() != PaymentStatus.APPROVED) {
            throw new InvalidPaymentException("Solo se pueden reembolsar pagos con estado APROBADO.");
        }

        if (amount <= 0 || amount > getMonto()) {
            throw new InvalidPaymentException("El monto especificado para reembolso es inválido.");
        }

        saldoPayPal += amount;
        System.out.printf("Reembolso PayPal realizado por: $%.2f%n", amount);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSaldoPayPal() {
        return saldoPayPal;
    }

    public void setSaldoPayPal(double saldoPayPal) {
        this.saldoPayPal = saldoPayPal;
    }

    @Override
    public String toString() {
        return "PayPalPayment{" +
                "email='" + email + '\'' +
                ", saldoPayPal=" + saldoPayPal +
                ", " + super.toString() +
                '}';
    }
}
