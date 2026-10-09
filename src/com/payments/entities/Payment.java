package com.payments.entities;

import com.payments.exceptions.InvalidPaymentException;

public abstract class Payment {

    public enum PaymentStatus {
        PENDING("PENDIENTE"),
        APPROVED("APROBADO"),
        REJECTED("RECHAZADO");

        private final String descripcion;

        PaymentStatus(String descripcion) {
            this.descripcion = descripcion;
        }

        @Override
        public String toString() {
            return descripcion;
        }
    }

    private final String id;
    private final double monto;
    private PaymentStatus estado;

    public Payment(String id, double monto) throws InvalidPaymentException {
        if (id == null || id.trim().isEmpty()) {
            throw new InvalidPaymentException("El ID del pago no puede estar vacío.");
        }

        if (monto <= 0) {
            throw new InvalidPaymentException("El monto debe ser mayor a cero.");
        }

        this.id = id;
        this.monto = monto;
        this.estado = PaymentStatus.PENDING;
    }

    public abstract void processPayment();

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
}
