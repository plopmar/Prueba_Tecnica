package com.PruebaTecnica.PruebaTecnica_PedroMLopez.dto.Response;

public class DependenciaResponseDTO {

    private String nombre;

    public DependenciaResponseDTO(String nombre) {
        this.nombre = nombre;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
