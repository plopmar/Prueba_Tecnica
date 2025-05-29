package com.PruebaTecnica.PruebaTecnica_PedroMLopez.dto.Response;

import com.PruebaTecnica.PruebaTecnica_PedroMLopez.model.EstadoCama;

import java.time.LocalDateTime;

public class CamaResponseDTO {
    private String etiqueta;
    private EstadoCama estadoCama;
    private LocalDateTime fechaAltaEnHospital;

    public CamaResponseDTO(String etiqueta, EstadoCama estadoCama, LocalDateTime fechaAltaEnHospital) {
        this.etiqueta = etiqueta;
        this.estadoCama = estadoCama;
        this.fechaAltaEnHospital = fechaAltaEnHospital;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public EstadoCama getEstadoCama() {
        return estadoCama;
    }

    public void setEstadoCama(EstadoCama estadoCama) {
        this.estadoCama = estadoCama;
    }

    public LocalDateTime getFechaAltaEnHospital() {
        return fechaAltaEnHospital;
    }

    public void setFechaAltaEnHospital(LocalDateTime fechaAltaEnHospital) {
        this.fechaAltaEnHospital = fechaAltaEnHospital;
    }
}
