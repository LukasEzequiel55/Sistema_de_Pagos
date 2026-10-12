package com.payments.entities;

import java.util.ArrayList;

public class PaymentManager {

    private final ArrayList<Payment> payments;

    public PaymentManager() {
        payments = new ArrayList<>();
    }

    public void registerPayment(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("No se puede registrar un pago nulo.");
        }

        if (findPaymentById(payment.getId()) != null) {
            throw new IllegalArgumentException("Ya existe un pago registrado con el ID: " + payment.getId());
        }

        payments.add(payment);
    }

    public void showPayments() {
        if (payments.isEmpty()) {
            System.out.println("No hay pagos registrados.");
            return;
        }

        System.out.println("\n===== PAGOS REGISTRADOS =====");
        for (Payment payment : payments) {
            System.out.println(payment);
        }
    }

    public Payment findPaymentById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }

        for (Payment payment : payments) {
            if (payment.getId().equalsIgnoreCase(id.trim())) {
                return payment;
            }
        }

        return null;
    }

    public double getTotalProcessed() {
        double total = 0;

        for (Payment payment : payments) {
            if (payment.getEstado() == Payment.PaymentStatus.APPROVED) {
                total += payment.getMonto();
            }
        }

        return total;
    }

    public void showTotalProcessed() {
        System.out.printf("\nTotal de pagos procesados y aprobados: $%.2f%n", getTotalProcessed());
    }

    public int getPaymentCount() {
        return payments.size();
    }
}
