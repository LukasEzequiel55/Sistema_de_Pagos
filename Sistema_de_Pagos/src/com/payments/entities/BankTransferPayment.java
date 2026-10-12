package com.payments.entities;

import com.payments.exceptions.InsufficientFundsException;
import com.payments.exceptions.InvalidPaymentException;

public class BankTransferPayment extends Payment{

    private int numCuenta;
    private String banco;
    private double saldoTransferencia;

    public BankTransferPayment(String id, double monto, int numCuenta, String banco, double saldoTransferencia) {
        super (id, monto);
        this.numCuenta = numCuenta;
        this.banco = banco;
        this.saldoTransferencia = saldoTransferencia;
    }//constructor BankTrasnferPayment


    public boolean procesarPagoTrans(double montoPagar) throws InvalidPaymentException, InsufficientFundsException{
        if (numCuenta <= 0 || banco.isBlank() || saldoTransferencia <= 0) {
            throw new InvalidPaymentException("Datos bancarios invalidos");
        }
        if (montoPagar <= 0) {
            throw new InvalidPaymentException("Monto invalido");
        }
        if (saldoTransferencia < montoPagar) {
            throw new InsufficientFundsException("Saldo insuficiente");
            String.format("Saldo insuficiente. Saldo disponible: $%.2f | Requerido: $%.2f", saldoTransferencia, getMonto());
        }
        saldoTransferencia -= montoPagar;
        setEstado(PaymentStatus.APPROVED);
        System.out.println("Transferencia aprobada");
        return true;
    }//ProcesarPago Transferencia

    public int getNumCuenta() {return numCuenta;}//get numeroCuenta

    public void setNumCuenta(int numCuenta) {this.numCuenta = numCuenta;} //set numeroCuenta

    public String getBanco() {return banco;}//get banco

    public void setBanco(String banco) {this.banco = banco;}//set banco

    public double getSaldoTransferencia() {return saldoTransferencia;}// get saldoTransferencia

    public void setSaldoTransferencia(double saldoTransferencia) {this.saldoTransferencia = saldoTransferencia;} //set saldoTransferencia

    @Override
    public String toString() {
        return "BankTransferPayment{" +
                "numCuenta=" + numCuenta +
                ", banco='" + banco + '\'' +
                ", saldoTransferencia=" + saldoTransferencia +
                '}' + super.toString();
    }
}
