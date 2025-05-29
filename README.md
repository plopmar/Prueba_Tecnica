# API REST Gestión de Camas Hospitalarias - Universidad de Murcia

Este repositorio contiene una API Rest desarrollada con Spring Boot como parte de una solución para la gestión de camas hospitalarias en centros médicos de la Universidad de Murcia.

## Tecnologías utilizadas

  - **Java 17**
  - **Spring Boot 3.5.0**
  - **Maven**
  - **Base de datos en memoria H2**
  - **JUnit**
  - **Postman**

## Endpoints creados

### Gestión de hospitales

| Método | Ruta                                             | Descripción                                                                 |
|--------|--------------------------------------------------|-----------------------------------------------------------------------------|
| GET    | /{idHospital}/camas                              | Lista todas las camas registradas en un hospital.                          |
| GET    | /{idHospital}/dependencias                       | Lista todas las dependencias del hospital especificado.                    |
| GET    | /{idHospital}/{idDependencia}/camas              | Lista todas las camas dentro de una dependencia concreta del hospital.     |
| PUT    | /{idHospital}/camas/{idCama}                     | Registra una cama en un hospital y una dependencia.                        |
| DELETE | /{idHospital}/camas/{idCama}                     | Elimina una cama del hospital.                                             |


### Gestión de camas

| Método | Ruta                          | Descripción                                                                 |
|--------|-------------------------------|-----------------------------------------------------------------------------|
| GET    | /camas/{idCama}               | Muestra la información completa de una cama: hospital, dependencia, estado.|
| POST   | /camas/{idCama}               | Crea una nueva cama con valores por defecto.                               |
| PUT    | /camas/{idCama}               | Actualiza el estado de una cama. No se permite si está asignada a un hospital.|
| DELETE | /camas/{idCama}               | Da de baja una cama del sistema.                                           |


## Cómo ejecutar el proyecto

```bash
git clone 
cd 
mvn spring-boot:run
```

## Una vez ejecutado

1. Accede a la consola de **H2 Database**:  
   [http://localhost:8080/h2-console](http://localhost:8080/h2-console)  
   - **JDBC URL:** `jdbc:h2:file:./data/db`  
   - **User Name:** `sa`

2. Accede a la interfaz de documentación **Swagger UI**:  
   [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
