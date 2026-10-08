package com.payments.entities;

import com.payments.exceptions.InvalidPaymentException;
import com.payments.exceptions.InsufficientFundsException;

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

        public Payment(String id, double monto) throws InvalidPaymentException {
            if (id == null || id.trim().isEmpty()) {
                throw  new InvalidPaymentException("El método que elegiste no es un método valido");
            }
            if ( monto <= 0) {
                throw new InvalidPaymentException("El monto debe ser mayor a cero");
            }
            this.id = id;
            this.monto = monto;
            this.estado = PaymentStatus.PENDING; // Todo pago inicia en PENDING
        }
        public abstract void processPayment() throws InsufficientFundsException, InvalidPaymentException;

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

        @Override
        public String toString() {
            return "Payment{" +
                    "id='" + id + '\'' +
                    ", monto=" + monto +
                    ", estado=" + estado +
                    '}';
        }
    } // class Payment