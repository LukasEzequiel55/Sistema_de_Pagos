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

### Conceptos de Programación Orientada a Objetos (POO) Aplicados
* **Clases y objetos:** Permiten modelar diferentes pasarelas de pago y crear instancias con estados y datos propios.
* **Encapsulamiento:** Se implementa utilizando modificadores `private` para proteger los atributos internos y exponiendo métodos de acceso (`getters` y `setters`) controlados.
* **Herencia:** Se utiliza `super()` para invocar constructores de la clase base (`Payment`), logrando reutilizar atributos comunes como el `id`, el `monto` y el `estado`.
* **Constructores:** Inicializan de forma segura los atributos de cada entidad y ejecutan validaciones iniciales mediante excepciones.
* **Métodos:** Agrupan la lógica de negocio para validar correos, cuentas, límites y procesar cobros o reembolsos.
* **Polimorfismo:** Se aplica mediante la sobrescritura de métodos (`@Override`) como `processPayment()`, `refund()` y `toString()` para adaptar el comportamiento según el tipo de pago específico.
* **Abstracción:** Cada clase agrupa y encapsula los datos y operaciones particulares de un método de pago, ocultando la complejidad de su procesamiento interno.
---

## Como nos distribuimos la participación

### Alexandra Pichardo
- `Payment`
- `CreditCardPayment`

### Hannia Victoria Reyes

- `PayPalPayment`
- `BankTransferPayment`

### Ulises Abraham Ortíz

- `Refundable`
- `Excepciones`
- `Validaciones`

### Daniel Rosas
- `PaymentManager`
- `PaymentsApp`
- `Pruebas de integración`


---
 ## Estructura de lo implementado

### Paquete: `com.payments.entities`

#### Clase Abstracta Padre `Payment`
* **Propósito:** Se implementó como la base general de cualquier pago. Evita la creación de pagos genéricos y centraliza los atributos comunes del negocio (`id`, `monto`, `estado`).
* **Comportamiento clave:** Define el método abstracto `processPayment()`, obligando a todas las clases hijas a implementar su propia lógica de procesamiento de cobro.

---

#### Enum `PaymentStatus`
* **Propósito:** Controla los estados del ciclo de vida de un pago (`PENDING`, `APPROVED`, `REJECTED`).
* **Ventajas técnicas:** Se creó como un enumerador independiente para centralizar los estados, evitando el uso de cadenas de texto (*strings*), previniendo errores tipográficos (*"stringly typed errors"*) y asegurando que los estados de las transacciones sean estrictamente consistentes en todos los módulos del equipo.

---

### Clase `PayPalPayment`
* **Ubicación:** `com.payments.entities`
* **Propósito:** Se encarga de simular y procesar pagos realizados por PayPal, validando el correo electrónico del usuario, verificando que el monto a pagar sea válido y comprobando la existencia de saldo suficiente.
* **Atributos:**
  * `email`: Correo electrónico de la cuenta de PayPal.
  * `saldoPayPal`: Saldo disponible para pagos.
* **Constructor:**
  * `PayPalPayment`: Inicializa los atributos y utiliza `super()` para invocar al constructor de la clase padre.
* **Métodos principales:**
  * `processPayment()` y `validarEmailPayPal()`: Procesan el pago verificando el correo, el monto y el saldo disponible. Si todo es correcto, descuentan el saldo y actualizan el estado a `APPROVED`.
  * *Getters y Setters*: Permiten consultar y modificar el correo electrónico y el saldo de PayPal.
  * `toString()`: Devuelve una representación en texto de los atributos del objeto combinados con la información de la clase padre.

---

### Clase `BankTransferPayment`
* **Ubicación:** `com.payments.entities`
* **Propósito:** Simula y procesa pagos mediante transferencias bancarias directas, validando datos de cuenta, montos y disponibilidad de fondos.
* **Atributos:**
  * `numCuenta`: Número de cuenta bancaria.
  * `banco`: Nombre de la institución bancaria.
  * `saldoTransferencia`: Saldo disponible para realizar transferencias.
* **Constructor:**
  * `BankTransferPayment`: Inicializa los atributos y utiliza `super()` para invocar al constructor de la clase padre.
* **Métodos principales:**
  * `processPayment()`: Valida que el número de cuenta sea válido, que el banco no esté vacío, que el monto sea mayor a 0 y que el saldo sea suficiente. Si se cumple, descuenta el monto y aprueba la transferencia.
  * *Getters y Setters*: Permiten consultar y modificar de forma controlada los atributos de la cuenta.
  * `toString()`: Devuelve una representación en texto de los datos bancarios junto con la información heredada de la clase padre.


---

### `Refundable` y Control de Errores

* **`Refundable` (`com.payments.interfaces`)**: Interfaz que dota selectivamente la capacidad de reembolsos totales (`refund()`) o parciales (`refund(double amount)`).
* **Excepciones Personalizadas (`com.payments.exceptions`)**: 
  * `InvalidPaymentException`: Bloquea datos corruptos o inválidos desde el constructor y previene reembolsos en pagos no aprobados.
  * `InsufficientFundsException`: Rechaza la transacción cambiando el estado a `REJECTED` cuando los fondos son insuficientes durante el cobro.
* **Sistema de Validaciones**:
  * **Constructor**: Garantiza que ningún objeto nazca con datos incorrectos lanzando `InvalidPaymentException`.
  * **Procesamiento**: Verifica límites y saldos al cobrar, descontando y aprobando el pago (`APPROVED`) o rechazándolo por falta de fondos.
  * **Reembolsos**: Valida que el pago esté aprobado y el monto sea válido antes de restaurar el saldo.
  * 
---



### Clase `PaymentManager`
* **Ubicación:** `com.payments.entities`
* **Propósito:** Administra y organiza el flujo de transacciones del sistema. Se encarga de registrar, almacenar dinámicamente, buscar y controlar el estado de los pagos (tarjeta, PayPal o transferencia), garantizando la integridad de los datos y calculando métricas financieras.
* **Atributos:**
  * Colección interna (`List<Payment>`): Almacena de forma dinámica y centralizada todas las transacciones procesadas.
* **Constructor:**
  * `PaymentManager`: Inicializa una lista vacía lista para recibir y administrar los pagos conforme se ejecutan.
* **Métodos Principales:**
  * **Registro y Validación:** Permiten registrar nuevos pagos asegurando que la información sea válida y evitando identificadores duplicados.
  * **Búsqueda y Visualización:** Permiten listar todas las transacciones registradas y localizar un pago específico mediante su identificador único (`ID`).
  * **Cálculo y Conteo:** Permiten obtener el número total de pagos registrados y sumar exclusivamente los montos de aquellos que tienen estado `APPROVED`, omitiendo los rechazados.


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
