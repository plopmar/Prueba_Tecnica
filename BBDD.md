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
- Con el objetivo de llevar un control más preciso sobre el estado de cada cama, se ha optado por registrar las fechas de alta y baja, tanto a nivel de sistema como por hospital. Uso de `LocalDateTime` para almacenar tanto fecha como la hora.
- **Control de transiciones de estado para camas**: se ha implementado una lógica que restringe los cambios entre estados permitidos para evitar inconsistencias. Las transiciones válidas son:

  - `LIBRE` → `OCUPADA`, `AVERIADA`
  - `OCUPADA` → `LIBRE`
  - `AVERIADA` → `EN_REPARACION`
  - `EN_REPARACION` → `LIBRE`
  - `BAJA` → no permite ningún cambio

  Esta lógica se implementa mediante un método `cambioValido(EstadoCama actual, EstadoCama nuevo)` que valida si la transición solicitada es aceptable antes de realizarla.
- Una vez que una cama es asignada a un hospital y cambia su estado a `OCUPADA`, no se permite ninguna otra transición de estado hasta que sea liberada (es decir, su relación con el hospital finalice).
- **Uso de diferentes DTO según el contexto de la respuesta**: se han definido estructuras de respuesta específicas para adaptar la información mostrada según el propósito del endpoint. **Por ejempo:**

  - **`CamaDetalleDTO`**: pensado para ofrecer una vista general del estado de una cama.
    **Ejemplo de respuesta:**

    ```json
    {
      "estado": "BAJA",
      "nombreHospital": null,
      "nombreDependencia": null,
      "etiqueta": "CAMA-99AA",
      "fechaAlta": "2025-05-30T11:34:57",
      "fechaBaja": "2025-05-30T11:35:46"
    }
    ```

  - **`CamaResponseDTO`**: diseñado para mostrar información relevante cuando la cama está asignada a un hospital y una dependencia.
    **Ejemplo de respuesta:**

    ```json
    {
      "etiqueta": "CAMA-20D7",
      "estadoCama": "OCUPADA",
      "fechaAltaEnHospital": "2025-05-30T11:38:47"
    }
    ```

---

## Esquema Base de Datos

<div align="center">

  <img src="https://github.com/user-attachments/assets/0e71a253-d8f1-4b65-84b8-45a76f5ef9e2" alt="Esquema Base de Datos" />

</div>

