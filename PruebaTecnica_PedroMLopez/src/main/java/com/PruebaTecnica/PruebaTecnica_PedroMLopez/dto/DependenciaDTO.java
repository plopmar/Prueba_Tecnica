package com.PruebaTecnica.PruebaTecnica_PedroMLopez.dto;


import jakarta.validation.constraints.NotNull;

public class DependenciaDTO {

    @NotNull
    private String nombre;

    public DependenciaDTO(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
