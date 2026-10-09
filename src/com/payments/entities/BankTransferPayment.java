package com.payments.entities;

import com.payments.exceptions.InsufficientFundsException;
import com.payments.exceptions.InvalidPaymentException;

public class BankTransferPayment extends Payment {

    private int numCuenta;
    private String banco;
    private double saldoTransferencia;

    public BankTransferPayment(String id, double monto, int numCuenta, String banco, double saldoTransferencia) {
        super(id, monto);
        this.numCuenta = numCuenta;
        this.banco = banco;
        this.saldoTransferencia = saldoTransferencia;
    }

    public boolean procesarPagoTrans(double montoPagar) {
        if (numCuenta <= 0 || banco == null || banco.isBlank() || saldoTransferencia < 0) {
            setEstado(PaymentStatus.REJECTED);
            throw new InvalidPaymentException("Datos bancarios inválidos.");
        }

        if (montoPagar <= 0) {
            setEstado(PaymentStatus.REJECTED);
            throw new InvalidPaymentException("Monto inválido.");
        }

        if (saldoTransferencia < montoPagar) {
            setEstado(PaymentStatus.REJECTED);
            throw new InsufficientFundsException(
                    String.format("Saldo insuficiente. Saldo disponible: $%.2f | Requerido: $%.2f", saldoTransferencia, montoPagar));
        }

        saldoTransferencia -= montoPagar;
        setEstado(PaymentStatus.APPROVED);
        System.out.printf("Transferencia bancaria aprobada por: $%.2f%n", montoPagar);
        return true;
    }

    @Override
    public void processPayment() {
        procesarPagoTrans(getMonto());
    }

    public int getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(int numCuenta) {
        this.numCuenta = numCuenta;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public double getSaldoTransferencia() {
        return saldoTransferencia;
    }

    public void setSaldoTransferencia(double saldoTransferencia) {
        this.saldoTransferencia = saldoTransferencia;
    }

    @Override
    public String toString() {
        return "BankTransferPayment{" +
                "numCuenta=" + numCuenta +
                ", banco='" + banco + '\'' +
                ", saldoTransferencia=" + saldoTransferencia +
                ", " + super.toString() +
                '}';
    }
}
