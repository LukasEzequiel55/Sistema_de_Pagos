package com.payments.entities;

import com.payments.interfaces.Refundable;
import com.payments.exceptions.InsufficientFundsException;
import com.payments.exceptions.InvalidPaymentException;

public abstract class PayPalPayment extends Payment implements Refundable {

    private String email;
    private double saldoPayPal;

    public PayPalPayment(String id, double monto, String email, double saldoPayPal) {
        super(id, monto);
        this.email = email;
        this.saldoPayPal = saldoPayPal;
    }//Constructor PayPalPayment

    public boolean validarEmailPayPal(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email != null && email.matches(regex);
    }//Validar email

    public boolean procesarPagoPay(double montoPagar) throws InvalidPaymentException, InsufficientFundsException{
        if (!validarEmailPayPal()) {
            System.out.println("Correo electronico invalido");
            return false;
        }
        if (montoPagar <= 0) {
            throw new InvalidPaymentException("Monto invalido");
        }
        if (saldoPayPal < montoPagar) {
            throw new InsufficientFundsException("Saldo insuficiente");
            String.format("Saldo insuficiente. Saldo disponible: $%.2f | Requerido: $%.2f", getMonto());
        }
        saldoPayPal -= montoPagar;
        System.out.println("Pago PayPal aprobado");
        return true;
    }//ProcesarPagoPayPal


    public String getEmail() {
        return email;
    }//get email

    public void setEmail(String email) {
        this.email = email;
    }//set email

    public double getSaldoPayPal() {
        return saldoPayPal;
    }//get saldoPayPal

    public void setSaldoPayPal(double saldoPayPal) {
        this.saldoPayPal = saldoPayPal;
    }//gel saldoPayPal

    @Override
    public String toString() {
        return "PayPalPayment{" +
                "email='" + email + '\'' +
                ", saldoPayPal=" + saldoPayPal +
                '}' + super.toString();
    }//to string
    @Override
    public void refund(double amount) throws InvalidPaymentException {
        refund(getMonto);
    }//toString

    @Override
    public void refund(double amount) throws InvalidPaymentException {
        if (getEstado() != PaymentStatus.APPROVED) {
            throw new InvalidPaymentException("Solo se pueden reembolsar pagos con estado APROBADO.");
        }
        if (amount <= 0 || amount > getMonto()) {
            throw new InvalidPaymentException("El monto especificado para reembolso es inválido.");
        }
        saldoPayPal += amount;
    }

}//Class PayPalPayment
