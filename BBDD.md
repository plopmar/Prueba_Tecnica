#  Estructura de la Base de Datos

##  Entidades

###  Hospital

| Campo         | Tipo              | Descripción                                     |
|---------------|-------------------|-------------------------------------------------|
| `id`          | Long              | Identificador único del hospital                |
| `nombre`      | String            | Nombre del hospital                             |
| `direccion`   | String            | Dirección del hospital                          |
| `dependencias`| List<Dependencia> | Lista de dependencias asociadas al hospital     |

---

###  Dependencia

| Campo      | Tipo           | Descripción                                           |
|------------|----------------|-------------------------------------------------------|
| `id`       | Long           | Identificador único de la dependencia                 |
| `nombre`   | String         | Nombre de la dependencia                              |
| `hospital` | Hospital       | Hospital al que pertenece (relación ManyToOne)       |
| `camas`    | List<Cama>     | Lista de camas asociadas a la dependencia            |

---

###  Cama

| Campo                  | Tipo            | Descripción                                                                 |
|------------------------|-----------------|-----------------------------------------------------------------------------|
| `id`                   | Long            | Identificador único de la cama                                              |
| `etiqueta`             | String          | Etiqueta única de la cama                                                   |
| `estado`               | Enum            | Estado actual (`LIBRE`, `OCUPADA`, `AVERIADA`, `EN_REPARACION`, `BAJA`)     |
| `hospital`             | Hospital        | Hospital al que pertenece la cama (relación ManyToOne)                     |
| `dependencia`          | Dependencia     | Dependencia donde se encuentra la cama (relación ManyToOne)                |
| `fechaAlta`            | LocalDateTime   | Fecha de alta en el sistema                                                 |
| `fechaBaja`            | LocalDateTime   | Fecha de baja en el sistema                                                 |
| `fechaAltaEnHospital`  | LocalDateTime   | Fecha de alta en el hospital                                                |
| `fechaBajaEnHospital`  | LocalDateTime   | Fecha de baja en el hospital                                                |

---

##  Relaciones

- Un **Hospital** ➝ muchas **Dependencias** (`OneToMany`)
- Una **Dependencia** ➝ un **Hospital** (`ManyToOne`)
- Una **Dependencia** ➝ muchas **Camas** (`OneToMany`)
- Una **Cama** ➝ una **Dependencia** (`ManyToOne`)
- Una **Cama** ➝ un **Hospital** (`ManyToOne`)

---

## Decisiones de diseño

- Uso de base de datos en memoria H2 para el desarrollo y pruebas. Esta base de datos se inicializa automaticamente con datos de prueba en las tablas de Hospital y Dependencias al arrancar la aplicación.
- Entidades relacionadas mediante anotaciones JPA (`OneToMany` y `ManyToOne`) para asegurar la integridad referencial y las operaciones CRUD.
- Uso de Clase ENUM (`EstadoCama`) para representar los posibles y únicos estados de una cama.
- Para tener un control más preciso de los estados de cada cama se ha decidido utilizar fechas de alta y baja tanto para el sistema como para cada hospital. Uso de `LocalDateTime` para almacenar tanto fecha como la hora.

---

## Esquema Base de Datos

<div align="center">

  <img src="https://github.com/user-attachments/assets/0e71a253-d8f1-4b65-84b8-45a76f5ef9e2" alt="Esquema Base de Datos" />

</div>

