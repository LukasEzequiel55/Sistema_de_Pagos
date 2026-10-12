# Sistema de Procesamiento de Pagos en Java

## Integrantes
- Hannia Victoria Reyes Rojas
- Alexandra Pichardo Angeles
- Daniel Rosas Monroy
- Ulises Abraham Ortíz Sánchez

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

### Alexandra Pichardo Angeles
- `Payment`
- `CreditCardPayment`

### Hannia Victoria Reyes Rojas

- `PayPalPayment`
- `BankTransferPayment`

### Ulises Abraham Ortíz Sánchez

- `Refundable`
- `Excepciones`
- `Validaciones`

### Daniel Rosas Monroy
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

### Clase `CreditCardPayment`
* **Ubicación:** `com.payments.entities`
* **Propósito:** Permite simular y procesar pagos mediante tarjetas de crédito. Valida los datos de la tarjeta, verifica que exista crédito disponible y permite realizar reembolsos de pagos aprobados.
* **Atributos:**
  * `cardNumber`: número de la tarjeta.
  * `cardHolder`: nombre del titular.
  * `creditLimit`: crédito disponible para realizar pagos.
* **Constructor:**
  * `CreditCardPayment`: inicializa los atributos, utiliza `super` para llamar al constructor de la clase padre y lanza `InvalidPaymentException` si los datos de la tarjeta son inválidos.
* **Métodos principales:**
  * `processPayment()`: verifica que el monto no supere el crédito disponible. Si se cumple, descuenta el monto y cambia el estado a `APPROVED`; de lo contrario, cambia a `REJECTED` y lanza `InsufficientFundsException`.
  * `refund()`: realiza el reembolso del monto total del pago.
  * *Getters*: permiten consultar atributos.
  * `toString()`: muestra la información del objeto, ocultando parte del número de tarjeta por seguridad.

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

### Interfaz `Refundable`
* **Ubicación:** `com.payments.interfaces`
* **Propósito:** Define el contrato de capacidad para aquellos métodos de pago que soportan devoluciones de dinero de manera selectiva.
* **Métodos principales:**
  * `refund()`: Realiza el reembolso por el total del monto original pagado.
  * `refund(double amount)`: Permite especificar un monto parcial para la devolución de fondos.

---

### Excepciones Personalizadas
* **Ubicación:** `com.payments.exceptions`
* **Clases:**
  * `InvalidPaymentException`: Excepción de tipo *Checked Exception* que se lanza cuando se detectan datos de entrada inválidos (por ejemplo: IDs vacíos, montos menores o iguales a cero, números de tarjeta incorrectos, correos electrónicos malformados o intentos de reembolso sobre pagos no aprobados).
  * `InsufficientFundsException`: Excepción de tipo *Checked Exception* que se lanza durante el procesamiento de la transacción cuando el saldo disponible o el límite de crédito en la cuenta es menor al monto requerido para el pago.

---

### Sistema de Validaciones
* **Validación en el Constructor (Fase de Inicialización):** 
  * Se ejecutan controles estrictos apenas se instancia un método de pago. 
  * Si los datos obligatorios de la tarjeta, la cuenta o PayPal son incorrectos, o si el monto inicial es inválido, el sistema interrumpe la creación del objeto lanzando de inmediato una `InvalidPaymentException`.
* **Validación en el Procesamiento (`processPayment()`):** 
  * Al momento de ejecutar el cobro, el sistema verifica que el saldo disponible o el límite de crédito cubra el monto del pago.
  * Si el dinero no alcanza, la transacción cambia su estado a `REJECTED` y se lanza una `InsufficientFundsException`. Si los fondos son suficientes, se descuenta el monto, se actualiza el estado a `APPROVED` y la operación se procesa con éxito.
* **Validación de Reembolsos (`refund()`):** 
  * Comprueba que el estado del pago sea estrictamente `APPROVED` y que el monto a devolver sea válido (mayor a cero y menor o igual al monto original pagado) antes de restaurar los fondos al saldo correspondiente.
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

### Clase `PaymentsApp`
* **Ubicación:** `com.payments`
* **Propósito:** Sirve como la clase principal y punto de entrada (`main`) de la aplicación. Su propósito es coordinar la ejecución general del programa, instanciar los diferentes tipos de pago (tarjeta de crédito, PayPal y transferencia bancaria), procesar las transacciones a través de sus respectivas reglas de negocio, gestionar excepciones y utilizar al administrador (`PaymentManager`) para consolidar y consultar los resultados.
* **Atributos:**
  * Instancias de control y variables de apoyo utilizadas dentro del flujo principal para ejecutar la simulación de la plataforma de pagos.
* **Métodos Principales:**
  * `main(String[] args)`: Método ejecutable que pone en marcha el sistema, crea el objeto `PaymentManager`, genera instancias de prueba para cada pasarela de pago, invoca los métodos de validación y procesamiento, maneja posibles excepciones (como fondos insuficientes o pagos inválidos) y muestra en consola los resultados, totales aprobados y operaciones de reembolso.


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
