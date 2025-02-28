# Marvel API Integration

Este proyecto es una API REST desarrollada con **Spring Boot** para gestionar y consumir datos de la API pública de **Marvel**. Proporciona endpoints para buscar personajes y cómics de Marvel, incluyendo detalles e imágenes, utilizando paginación y filtros.

## Características principales

- Gestión de **Personajes**:
  - Listar personajes con filtros (nombre, cómics, series).
  - Obtener información detallada de un personaje.
- Gestión de **Cómics**:
  - Listar cómics con paginación.
  - Obtener detalles de un cómic.
- Seguridad básica deshabilitada para pruebas (se puede ampliar con Spring Security).
- Consumo de la API de Marvel utilizando hash MD5 para la autenticación.

## Tecnologías utilizadas

- **Java 23**
- **Spring Boot**  
  - **Spring Web**: Desarrollo de la API REST.
  - **Spring Security**: Configuración de seguridad básica.
  - **Spring Context**: Gestión de Beans.
- **Lombok**: Reducción del código repetitivo.
- **Jackson**: Procesamiento de formatos JSON.
- **MySQL**: Base de datos de ejemplo configurada para pruebas.
- **RestTemplate**: Para realizar llamadas HTTP a la API de Marvel.
- **Jakarta EE**: Integración de JPA y API empresarial.

---

## Requisitos previos

- **Java 23 o superior**.
- **Maven 3.6+**.
- **Base de datos MySQL** configurada correctamente.
- Una cuenta en [Marvel API](https://developer.marvel.com/) para obtener tu clave pública y privada.

---

## Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/raclsodev/marvel-api-integration.git
cd marvel-api-integration
```

### 2. Configurar las propiedades de la aplicación

Edita el archivo `src/main/resources/application-desa.properties` para incluir las credenciales de tu base de datos y claves de la API de Marvel:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tu_base_datos
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA
marvel.apikey=TUPUBLICKEY
marvel.privatekey=TUPRIVATEKEY
```

### 3. Construir el proyecto y ejecutar

```bash
mvn clean install
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`.

---

## Endpoints disponibles

### **Personajes**

#### Listar personajes
```http
GET /characters
```
**Parámetros opcionales:**
- `name` (String): Filtrar por nombre del personaje.
- `comics` (Array): Filtrar por IDs de cómics.
- `series` (Array): Filtrar por IDs de series.
- `offset` (Long): Paginación (inicio).
- `limit` (Long): Paginación (número de resultados).

#### Obtener detalles de un personaje
```http
GET /characters/{characterId}
```

### **Cómics**

#### Listar cómics
```http
GET /comics
```
**Parámetros opcionales:**
- `characterId` (Long): Filtrar cómics asociados con un personaje.
- `offset` (Long): Paginación (inicio).
- `limit` (Long): Paginación (número de resultados).

#### Obtener detalles de un cómic
```http
GET /comics/{comicId}
```

---

## Estructura de la aplicación

```plaintext
src/
├── main/
│   ├── java/com/test/api/marvel/
│   │   ├── controller/       # Controladores REST
│   │   ├── dto/              # DTOs de datos (cómics, personajes, paginación, etc.)
│   │   ├── exception/        # Excepciones personalizadas
│   │   ├── mapper/           # Mapeadores JSON <-> DTO
│   │   ├── persistence/      # Conexión con la API externa Marvel
│   │   ├── security/         # Configuración básica de seguridad
│   │   ├── service/          # Interfaces de servicios
│   │   ├── service/impl/     # Implementación de servicios
│   │   ├── util/             # Utilidades (MD5, Beans comunes, etc.)
├── resources/
│   ├── application-desa.properties  # Configuración de propiedades
├── test/                            # Pruebas unitarias
```

---

## Ejemplo de consumo de la API de Marvel

El servicio está configurado para autenticar las peticiones a la API de Marvel automáticamente utilizando un hash MD5:

- **Estructura del hash**:  
  `hash = MD5(ts + privateKey + publicKey)`

El componente `MarvelAPIConfig` genera y envía los parámetros necesarios (`ts`, `apikey`, y `hash`) en todas las llamadas a la API de Marvel.

---

## Pruebas

Este proyecto incluye pruebas unitarias básicas utilizando Spring Boot:

```bash
mvn test
```

---

## Autores

- **[RaclosDev]**  
  GitHub: [RaclosDev](https://github.com/RaclosDev)  
  Email: [RaclosDevl@gmail.com](mailto:RaclosDev@gmail.com)

---

## Contribuciones

¡Las contribuciones son bienvenidas! Si deseas contribuir, por favor abre un issue para discutir cambios importantes antes de realizar un pull request.

---

## Licencia

Este proyecto está bajo la licencia **MIT**. Consulta el archivo `LICENSE` para más detalles.
