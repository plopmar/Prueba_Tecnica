package com.PruebaTecnica.PruebaTecnica_PedroMLopez.dto;

import com.PruebaTecnica.PruebaTecnica_PedroMLopez.model.EstadoCama;
import jakarta.validation.constraints.NotNull;

public class EstadoCamaDTO {

    @NotNull
    private EstadoCama estadoCama;

    public EstadoCama getEstadoCama() {
        return estadoCama;
    }

    public void setEstadoCama(EstadoCama estadoCama) {
        this.estadoCama = estadoCama;
    }
}
