# Sistema de Procesamiento de Pagos en Java

## Integrantes
- Hannia Victoria
- Alexandra Pichardo
- Daniel Rosas Monroy
- Ulises Abraham Ortíz Sanchez

---

## Descripción del Proyecto
Sistema en Java enfocado en la gestión y procesamiento de pagos con arquitectura orientada a objetos (POO). Implementa clases abstractas, interfaces, polimorfismo, validaciones estrictas y manejo explícito de excepciones personalizadas para procesar transacciones financieras de forma segura.

---

## Tecnologías Utilizadas
* **Lenguaje:** Java SE (JDK 11 o superior)
* **Paradigma:** Programación Orientada a Objetos (POO)
* **Librerías/Frameworks:** Java Collections Framework

---

## Cómo Ejecutar el Proyecto

1. Clonar o descargar la estructura del repositorio.
2. Abrir una terminal en la raíz del proyecto.
3. Compilar todas las clases desde la carpeta `src`:
   ```bash
   javac -d bin src/com/payments/interfaces/*.java src/com/payments/exceptions/*.java src/com/payments/entities/*.java src/com/payments/PaymentsApp.java
   ```
4. Ejecutar la aplicación:
   ```bash
   java -cp bin com.payments.PaymentsApp
   ```

---

## Funcionalidades Implementadas

* **01 — Métodos de Pago:** Soporte para Tarjeta de Crédito (`CreditCardPayment`), PayPal (`PayPalPayment`) y Transferencia Bancaria (`BankTransferPayment`).
* **02 — Reglas de Procesamiento y Validaciones:** Verificación de límite de crédito, disponibilidad de saldo y sintaxis/formato de campos obligatorios.
* **03 — Interfaz `Refundable`:** Capacidad de realizar reembolsos parciales o totales aplicada a métodos de pago compatibles.
* **04 — Manejo de Excepciones Personalizadas:** Captura y prevención de fallos mediante `InsufficientFundsException` e `InvalidPaymentException`.
* **05 — Gestión Coleccionable y Polimórfica (`PaymentManager`):** Registro, búsqueda por ID, listado detallado y cálculo del total acumulado de transacciones aprobadas mediante `ArrayList<Payment>`.
