<h1 align="center">Drone Flight Control System</h1>

<p align = "center">
  <img src = https://github.com/user-attachments/assets/462e359f-9af8-45b7-9ca0-4ca52ae232e8/>
</p>

Aplicación API RESTful desarrollada en **Spring Boot 3**, que permite gestionar **drones** en una **matriz de vuelo**. Incluye creación, edición, ejecución de órdenes y validación de la estructurada de datos. Está pensada como solución backend para un sistema de control de navegación automatizada de drones.

---

## 📋 Índice
1. [🛸 Tecnologías usadas](#-tecnologías-usadas)
2. [📖 Características](#-características)
3. [📁 Estructura del proyecto](#-estructura-del-proyecto)
4. [🧠 Patrones de diseño aplicados](#-patrones-de-diseño-aplicados)
5. [🛠️ Instalación y ejecución](#️-instalación-y-ejecución)
6. [📡 Endpoints principales](#-endpoints-principales)
7. [🧪Validaciones y manejo de errores](#-validaciones-y-manejo-de-errores) 


---

## 🛸 Tecnologías usadas
- **Java 21**
- **Maven**
- **MySql**
- **Spring Boot 3.4.4**
- **Spring Data JPA**
- **Hibernate**
- **Lombok**
- **JUnit + Mockito + Spring Test (testing)**
- **Jacoco (informe de test)**
- **Logback + Logstash Encoder (logging JSON)**
- **Swagger / OpenAPI 3 (documentación de endpoints)**
- **GitHub (versiones del código)**

---

## 📖 Características
- Creación, edición y eliminación de drones y matrices.
- Envío de múltiples órdenes de vuelo a uno o varios drones.
- Validación de coordenadas y restricciones de espacio.
- Excepciones personalizadas para todos los posibles errores.
- Validación robusta de entrada con anotaciones `@Valid`.
- Logs en formato JSON con rotación automática.
- API documentada con Swagger (OpenAPI).

---

## 📁 Estructura del proyecto
```plaintext
src/
└── main/
    ├── java/
    │   └── space.flight/
    │       logs/
    │       └── flight.log          # Archivo de logs rotativo
    │       ├── conf/               # Configuración (OpenAPI)
    │       ├── controller/         # Controladores REST
    │       ├── dto/                # Objetos de transferencia de datos
    │       ├── entity/             # Entidades JPA
    │       ├── exception/          # Excepciones personalizadas y global handler
    │       ├── mapper/             # MapStruct mappers
    │       ├── repository/         # Repositorios Spring Data
    │       ├── service/            # Lógica de negocio
    │       └── FlightApplication   # Clase principal
    ├── resources/
    │   ├── application.properties  # Configuración de base de datos y entorno
    │   └── logback.xml             # Logging estructurado JSON
    └── 
```
---

## 🧠 Patrones de diseño aplicados
En mi proyecto aplico varios patrones de diseño para mantener una arquitectura clara, modular y escalable:

---

### Patrón MVC (Model - View - Controller)
Organizo el proyecto en capas bien definidas:

- **controller:** Expongo los endpoints REST.
- **service:** Donde tengo la lógica de negocio.
- **entity:** Representa el modelo de datos que necesito.
- **dto & mapper:** Encapsulan y transforman los datos entre capa web y dominio.

### Patrón DTO (Data Transfer Object)
Se utilizan clases DTO para:

- Evitar exponer directamente las entidades JPA.
- Validar la entrada del usuario (`@Valid`, `@NotBlank`, etc.).
- Reducir el acoplamiento entre capas.

### Patrón Repository
Uso del repositorio JPA para encapsular el acceso a la base de datos.

### Patrón Mapper
Separación de la lógica de mapeo entre DTO y Entity usando clases como DronMapper y MatrizMapper.

### Patrón Singleton (implícito)
Spring gestiona automáticamente los @Service, @Repository y @Controller como beans singleton, asegurando una única instancia por contexto.

### Manejo centralizado de errores: @ControllerAdvice + @ExceptionHandler
Validaciones de entrada con mensajes personalizados.

Excepciones específicas como:
- DroneNotFoundException
- UnknownOrientationException
- InvalidMatrixDimensionsException

### Builder (Lombok)
Uso de @Builder en entidades para facilitar la construcción fluida y controlada de objetos complejos.

---

## 🛠️ Instalación y ejecución

Sigue los pasos para clonar, compilar y ejecutar el proyecto:

### 🔽 Clonar el repositorio
git clone https://github.com/asanmal/flight.git
cd flight

### ⚙️ Requisitos previos
- Java 21
- Spring Boot
- Maven
- MySQL Server
- IDE recomendado: IntelliJ IDEA

### 🗃️ Configuración de base de datos
- Crea una base de datos llamada vuelos_db en MySQL:
CREATE DATABASE vuelos_db;

- Verifica el archivo application.properties:
Ajusta los valores de username y password según tu entorno.

### ▶️ Compilar y ejecutar el proyecto
Desde tu IDE ejecuta la clase:
space.flight.FlightApplication


### 🧪 Acceder a la API
Una vez arrancado el proyecto:
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- API Base URL: http://localhost:8080/api/vuelos

---

## 📡 Endpoints principales

A continuación se listan los endpoints REST más relevantes expuestos por la API:

### 🚁 Drones

| Método | Endpoint                                   | Descripción                             |
|--------|--------------------------------------------|-----------------------------------------|
| POST   | `/api/vuelos/dron/create`                  | Crear un nuevo dron                     |
| PUT    | `/api/vuelos/dron/edit/{dronId}`           | Editar un dron existente                |
| DELETE | `/api/vuelos/dron/delete/{dronId}`         | Eliminar un dron                        |
| GET    | `/api/vuelos/dron/list`                    | Listar todos los drones                 |
| GET    | `/api/vuelos/dron/list/{matrizId}`         | Listar drones por ID de matriz          |
| GET    | `/api/vuelos/dron/list/{x}/{y}/{matrizId}` | Buscar dron por coordenadas             |

### 🧭 Vuelos

| Método | Endpoint                             | Descripción                             |
|--------|--------------------------------------|-----------------------------------------|
| POST   | `/api/vuelos/flight/single/{dronId}` | Ejecutar ordenes en un dron específico  |
| POST   | `/api/vuelos/flight/group`           | Ejecutar ordenes en múltiples drones    |

### 🗺️ Matrices

| Método | Endpoint                                | Descripción                        |
|--------|-----------------------------------------|------------------------------------|
| POST   | `/api/vuelos/matriz/create`             | Crear una nueva matriz             |
| PUT    | `/api/vuelos/matriz/edit/{matrizId}`    | Editar una matriz                  |
| DELETE | `/api/vuelos/matriz/delete/{matrizId}`  | Eliminar matriz si no tiene drones |
| GET    | `/api/vuelos/matriz/list`               | Listar todas las matrices          |

---

## ⚠️ Validaciones y manejo de errores
El sistema cuenta con un completo control de errores mediante excepciones personalizadas que mejoran la claridad y el mantenimiento del código. Estas excepciones están gestionadas globalmente mediante @ControllerAdvice, ofreciendo respuestas adecuadas y mensajes explicativos en cada caso.

- Las principales excepciones utilizadas en el proyecto son:

  - MatrixNotFoundException: se lanza cuando se intenta acceder a una matriz que no existe en la base de datos.
  - DroneNotFoundException: aparece si el dron especificado no existe o no ha sido encontrado en la matriz.
  - DroneOutOfMatrixException: se utiliza para indicar que un dron se ha posicionado o intentado mover fuera de los límites de la matriz.
  - DronePositionOccupiedException: se lanza cuando un dron intenta situarse en una posición que ya está ocupada por otro dron dentro de la misma matriz.
  - InvalidMatrixDimensionsException: se lanza si se intenta crear una matriz con dimensiones inválidas (por ejemplo, negativas) o eliminar una que aún contiene drones activos.
  - InvalidOrderException: se lanza cuando se recibe una orden no válida en la ejecución de comandos para un dron.
  - UnknownOrientationException: se lanza si se especifica una orientación no válida, es decir, que no está contemplada en el enum Orientacion.
  - Estas excepciones están diseñadas para trabajar en conjunto con las validaciones de entrada de datos y proporcionar al usuario errores comprensibles y útiles con su respectivo código HTTP (400, 404, 409, etc.).
