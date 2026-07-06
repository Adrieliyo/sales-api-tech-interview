# Sales API - Sistema de Gestión de Ventas

API REST desarrollada en **Spring Boot** para gestionar operaciones de ventas, incluyendo clientes, productos, ventas y sus detalles.

## Características

- Autenticación y autorización con JWT
- Gestión de clientes, productos y ventas
- Control de inventario con descuento automático de stock
- Cancelación de ventas con validaciones
- Documentación OpenAPI/Swagger automática
- Paginación de resultados
- Manejo centralizado de excepciones
- Validaciones de negocio separadas

---

## Requisitos Previos

Asegúrate de tener instalado:

- **Java 8**
- **Apache Maven 3.6+**
- **MySQL 8+**

Verifica las versiones:
```bash
java -version
mvn -version
mysql --version
```

---

## Configuración Inicial del Proyecto

### 1. Clonar el Repositorio

```bash
git clone <repositorio-url>
cd sales-api
```

### 2. Configurar la Base de Datos MySQL

#### Crear la base de datos:

```sql
CREATE DATABASE sales; 
```

**Nota:** Las credenciales por defecto están configuradas en `src/main/resources/application.properties`. Es necesario modifícarlas según tu ambiente de desarrollo.

### 3. Verificar Configuración de Base de Datos

Edita `src/main/resources/application.properties` si es necesario:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sales
spring.datasource.username=root
spring.datasource.password=Y3Tribe2303
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update  # Los cambios se crearán automáticamente
```

### 4. Compilar el Proyecto

```bash
mvn clean package
```

O sin ejecutar tests:

```bash
mvn clean package -DskipTests
```

---

## Ejecución del Proyecto

### Opción 1: Con Maven

```bash
mvn spring-boot:run
```

### Opción 2: Ejecutar la clase principal del proyecto


Está ubicada dentro de `src/main/java/com/macronnect/sales_api/SalesApiApplication.java`


Una vez ejecutada la aplicación, estará disponible en **http://localhost:8080**

---

## Ejecutar Tests

### Ejecutar todos los tests

```bash
mvn test
```

### Ejecutar tests de una clase específica

```bash
mvn test -Dtest=SaleValidationTest
```


### Tests incluidos

- `SalesApiApplicationTests`: Test básico de contexto de Spring
- `SaleValidationTest`: Validaciones de ventas
- `SaleServiceImplTest`: Pruebas del servicio de ventas

**Para probar los test mediante la herramienta de Maven ya incluida dentro de IntelliJ es necesario ir a la siguiente ruta:**<br>
`src/test/java/com/macronnect/sales_api/`

---

## Documentación de API

Una vez que la aplicación esté corriendo, accede a la documentación interactiva:

- **Swagger UI:** http://localhost:8080/swagger-ui.html


---

## Arquitectura y Decisiones de Diseño

### **Patrón de Capas (Layered Architecture)**
Se manejó un patrón de capas como requerimiento principal de la prueba técnica, esto con la finalidad
de separar las responsabilidades, facilitar el testing y el mantenimiento de la API.

### **Autenticación y Autorización con JWT**

**Flujo de seguridad:**

1. Usuario login con `username` y `password`
2. Servidor valida credenciales
3. Servidor genera token JWT
4. Cliente incluye token en header `Authorization: Bearer <token>`
5. Servidor valida token en cada request

**Componentes:**
- `JwtTokenProvider`: Generación y validación de tokens
- `JwtAuthenticationFilter`: Filtro para validar JWT en requests
- `CustomUserDetailsService`: Carga usuarios de BD
- `SecurityConfig`: Configuración de seguridad

**Ubicación:** `src/main/java/com/macronnect/sales_api/security/`

**Configuración:**
```properties
jwt.secret=SalesApiSecretKeyForJwtAuthentication2026
jwt.expiration=86400000  # 24 horas en ms
```

### **Inicialización de Datos**

La aplicación inicializa usuarios de demostración al inicio.

**Ubicación:** `src/main/java/com/macronnect/sales_api/SalesApiApplication.java`

**Usuarios creados:**
- `admin` / `123456`

### **OpenAPI/Swagger**

Documentación automática de la API generada por SpringDoc OpenAPI.

**Anotaciones usadas:**
- `@Tag`: Agrupar operaciones
- `@Operation`: Describir operación
- `@ApiResponse`: Documentar respuestas

**Configuración:** `src/main/java/com/macronnect/sales_api/config/OpenApiConfig.java`

---

## Estructura del Proyecto

Me basé en el siguiente artículo de Medium para poder seguir buenas prácticas dentro del proyecto:
[Spring Boot Project Structure Explained (Best Practices)](https://medium.com/@anandjeyaseelan10/spring-boot-project-structure-explained-best-practices-c2ba46ea57eb)<br>


```
sales-api/
├── src/
│   ├── main/java/com/macronnect/sales_api/
│   │   ├── SalesApiApplication.java          # Entrada principal
│   │   ├── config/                           # Configuraciones
│   │   │   ├── OpenApiConfig.java           # Swagger/OpenAPI
│   │   │   ├── PageableConfig.java          # Paginación
│   │   │   └── SecurityConfig.java          # Seguridad y JWT
│   │   ├── controller/                       # Controllers (REST endpoints)
│   │   │   ├── AuthController.java
│   │   │   ├── ClientController.java
│   │   │   ├── ProductController.java
│   │   │   └── SaleController.java
│   │   ├── service/                          # Servicios (Lógica negocio)
│   │   │   └── impl/
│   │   │       ├── ClientServiceImpl.java
│   │   │       ├── ProductServiceImpl.java
│   │   │       └── SaleServiceImpl.java
│   │   ├── repository/                       # Repositorios (Acceso datos)
│   │   │   ├── ClientRepository.java
│   │   │   ├── ProductRepository.java
│   │   │   ├── SaleRepository.java
│   │   │   └── UserRepository.java
│   │   ├── model/
│   │   │   ├── dto/                          # Data Transfer Objects
│   │   │   │   ├── auth/
│   │   │   │   ├── client/
│   │   │   │   ├── product/
│   │   │   │   ├── sale/
│   │   │   │   └── saleDetail/
│   │   │   ├── entity/                       # Entidades JPA
│   │   │   │   ├── BaseEntity.java
│   │   │   │   ├── User.java
│   │   │   │   ├── Client.java
│   │   │   │   ├── Product.java
│   │   │   │   ├── Sale.java
│   │   │   │   └── SaleDetail.java
│   │   │   └── enums/                        # Enumeraciones
│   │   │       ├── SaleStatus.java
│   │   │       └── Status.java
│   │   ├── mapper/                           # Transformadores Entity ↔ DTO
│   │   │   ├── ClientMapper.java
│   │   │   ├── ProductMapper.java
│   │   │   └── SaleMapper.java
│   │   ├── exception/                        # Excepciones personalizadas
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   ├── ErrorResponse.java
│   │   │   ├── ResourceNotFoundException.java
│   │   │   ├── DuplicateResourceException.java
│   │   │   ├── client/
│   │   │   ├── product/
│   │   │   └── sale/
│   │   ├── security/                         # Seguridad y JWT
│   │   │   ├── JwtTokenProvider.java
│   │   │   ├── JwtAuthenticationFilter.java
│   │   │   └── CustomUserDetailsService.java
│   │   ├── validation/                       # Validaciones de negocio
│   │   │   ├── SaleValidation.java
│   │   │   └── ProductValidation.java
│   │   └── util/                             # Utilidades
│   ├── main/resources/
│   │   └── application.properties            # Configuración de la aplicación
│   └── test/java/                            # Tests unitarios
│       └── com/macronnect/sales_api/
│           ├── service/
│           └── validation/
└──pom.xml                                   # Dependencias Maven



```

---

## Endpoints Principales

### Autenticación

- `POST /auth/login` - Login y obtener JWT token <br><br>
Es necesario copiar el Token que retorna este endpoint para utilizarlo como Bearer Token dentro de la documentación Swagger.


### Clientes

- `GET /clients` - Listar clientes (paginado)
- `GET /clients/{id}` - Obtener cliente por ID
- `POST /clients` - Crear nuevo cliente
- `PUT /clients/{id}` - Actualizar cliente
- `DELETE /clients/{id}` - Eliminar cliente (soft delete)

### Productos

- `GET /products` - Listar productos (paginado)
- `GET /products/{id}` - Obtener producto por ID
- `POST /products` - Crear nuevo producto
- `PUT /products/{id}` - Actualizar producto
- `DELETE /products/{id}` - Eliminar producto (soft delete)

### Ventas

- `GET /sales` - Listar ventas (paginado)
- `GET /sales/{id}` - Obtener venta por ID
- `POST /sales` - Crear nueva venta
- `PUT /sales/{id}/cancel` - Cancelar venta

---

## Configuración por Ambiente

### Development (por defecto)

```properties
spring.jpa.show-sql=true
logging.level.org.springframework.security=DEBUG
spring.jpa.hibernate.ddl-auto=update
```

### Production (recomendado)

```properties
spring.jpa.show-sql=false
logging.level.org.springframework.security=INFO
spring.jpa.hibernate.ddl-auto=validate
server.servlet.context-path=/api
```


---

## En caso de tener más tiempo para desarrollar
Me hubiese gustado manejar las respuestas de error comunes para JWT que son los errores 401 Unauthorized y 403 Forbidden
Además de extender mi clase ErrorResponse con un campo path para indicar la ruta donde se produjo el error.

Hubiera implementado un mejor manejo de usuarios para la API y establecer cookies para mantener el Access token y el Refresh Token dentro de la misma.

También me hubiese gustado agregar una tabla de secuencias para evitar el riesgo de duplicados por concurrencia en cuanto al registro de ventas. Ya que por ahora si dos usuarios registran una venta exactamente al mismo tiempo, ambos pueden obtener el mismo número de folio antes de guardar la venta.
