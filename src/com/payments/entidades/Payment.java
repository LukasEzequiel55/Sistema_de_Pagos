package com.payments.entidades;

public class Payment {

    enum PaymentStatus{
        PENDING ("PENDIENTE"),
        APPROVED ("APROBADO"),
        REJECTED ("RECHAZADO");
    }

    public abstract class Payment {
        private String id;
        private double monto;
        private PaymentStatus estado;

    public Payment(String id, double amount) {
        this.id = id;
        this.monto = amount;
        this.estado = PaymentStatus.PENDING; // Todo pago inicia en PENDING
    }
        public abstract void processPayment() throws Exception;

        public String getId() {
            return id;
        }

        public double getMonto() {
            return monto;
        }

        public PaymentStatus getEstado() {
            return estado;
        }

        protected void setEstado(PaymentStatus estado) {
            this.estado = estado;
        }

} // class Payment
