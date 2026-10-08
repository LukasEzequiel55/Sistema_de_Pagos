package com.payments;

import com.payments.entities.BankTransferPayment;
import com.payments.entities.CreditCardPayment;
import com.payments.entities.PayPalPayment;
import com.payments.entities.Payment;
import com.payments.entities.PaymentManager;
import com.payments.exceptions.InsufficientFundsException;
import com.payments.exceptions.InvalidPaymentException;
import com.payments.interfaces.Refundable;

public class PaymentsApp {

    public static void main(String[] args) {
        PaymentManager manager = new PaymentManager();

        System.out.println("========================================");
        System.out.println("      SISTEMA DE PROCESAMIENTO DE PAGOS");
        System.out.println("========================================");

        // 1. Crear diferentes métodos de pago.
        Payment tarjeta = new CreditCardPayment(
                "CC-001", 1000.00, "4111111111111111", "Daniel Rosas", 5000.00);

        Payment paypal = new PayPalPayment(
                "PP-001", 850.00, "cliente@example.com", 2000.00);

        Payment transferencia = new BankTransferPayment(
                "BT-001", 1200.00, 12345678, "Banco Ejemplo", 3000.00);

        // 2. Procesar pagos exitosos y manejar excepciones.
        processAndRegister(manager, tarjeta);
        processAndRegister(manager, paypal);
        processAndRegister(manager, transferencia);

        // 3. Intentar procesar un pago rechazado.
        Payment pagoRechazado = new CreditCardPayment(
                "CC-002", 6000.00, "4000000000000002", "Cliente Prueba", 500.00);

        processAndRegister(manager, pagoRechazado);

        // 4. Mostrar los pagos registrados.
        manager.showPayments();

        // 5. Buscar un pago mediante su ID.
        System.out.println("\n===== BÚSQUEDA POR ID =====");
        Payment encontrado = manager.findPaymentById("PP-001");

        if (encontrado != null) {
            System.out.println("Pago encontrado: " + encontrado);
        } else {
            System.out.println("No se encontró el pago solicitado.");
        }

        // 6. Buscar un pago que no existe.
        Payment noEncontrado = manager.findPaymentById("NO-EXISTE");
        if (noEncontrado == null) {
            System.out.println("La búsqueda NO-EXISTE no encontró ningún pago.");
        }

        // 7. Mostrar el total de pagos aprobados.
        manager.showTotalProcessed();

        // 8. Realizar un reembolso mediante una referencia Refundable.
        System.out.println("\n===== REEMBOLSO =====");
        Payment pagoParaReembolso = manager.findPaymentById("PP-001");

        if (pagoParaReembolso instanceof Refundable) {
            try {
                Refundable refundable = (Refundable) pagoParaReembolso;
                refundable.refund(200.00);
            } catch (InvalidPaymentException e) {
                System.out.println("No fue posible realizar el reembolso: " + e.getMessage());
            }
        }

        System.out.println("\nSaldo de PayPal después del reembolso: $" + ((PayPalPayment) paypal).getSaldoPayPal());
    }

    private static void processAndRegister(PaymentManager manager, Payment payment) {
        System.out.println("\nProcesando pago " + payment.getId() + "...");

        try {
            payment.processPayment();
            manager.registerPayment(payment);
            System.out.println("Pago registrado correctamente.");
        } catch (InsufficientFundsException e) {
            paymentRejected(payment, e.getMessage());
            manager.registerPayment(payment);
        } catch (InvalidPaymentException e) {
            paymentRejected(payment, e.getMessage());
            manager.registerPayment(payment);
        } catch (IllegalArgumentException e) {
            System.out.println("No fue posible registrar el pago: " + e.getMessage());
        }
    }

    private static void paymentRejected(Payment payment, String message) {
        System.out.println("Pago rechazado (" + payment.getId() + "): " + message);
    }
}
