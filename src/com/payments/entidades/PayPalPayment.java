package com.payments.entidades;

public class PayPalPayment {

    private String email;
    private double saldoPayPal;

    public PayPalPayment(String id, double monto, String email, double saldoPayPal) {
        super (id, monto);
        this.email = email;
        this.saldoPayPal = saldoPayPal;
    }//Constructor PayPalPayment

    public boolean validarEmailPayPal(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email != null && email.matches(regex);
    }//Validar email

    public boolean procesarPagoPay(double montoPagar) {
        if (!validarEmailPayPal()) {
            System.out.println("Correo electronico invalido");
            return false;
        }
        if (montoPagar <= 0) {
            System.out.println("Monto invalido");
            return false;
        }
        if (saldoPayPal < montoPagar) {
            System.out.println("Saldo insuficiente");
            return false;
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
    }//toString
}//Class PayPalPayment
