package com.payments.entities;

public class BankTransferPayment {

    private int numCuenta;
    private String banco;
    private double saldoTransferencia;

    public BankTransferPayment(int numCuenta, String banco, double saldoTransferencia) {
        this.numCuenta = numCuenta;
        this.banco = banco;
        this.saldoTransferencia = saldoTransferencia;
    }//constructor BankTrasnferPayment


    public boolean procesarPagoTrans(double montoPagar) {
        if (numCuenta <= 0 || banco.isBlank() || saldoTransferencia <= 0) {
            System.out.println("Datos bancarios invalidos");
            return false;
        }
        if (montoPagar <= 0) {
            System.out.println("Monto invalido");
            return false;
        }
        if (saldoTransferencia < montoPagar) {
            System.out.println("Saldo insuficiente");
            return false;
        }
        saldoTransferencia -= montoPagar;
        System.out.println("Transferencia aprobada");
        return true;
    }//ProcesarPago Transferencia

    public int getNumCuenta() {return numCuenta;}//get numeroCuenta

    public void setNumCuenta(int numCuenta) {this.numCuenta = numCuenta;} //set numeroCuenta

    public String getBanco() {return banco;}//get banco

    public void setBanco(String banco) {this.banco = banco;}//set banco

    public double getSaldoTransferencia() {return saldoTransferencia;}// get saldoTransferencia

    public void setSaldoTransferencia(double saldoTransferencia) {this.saldoTransferencia = saldoTransferencia;} //set saldoTransferencia
}
