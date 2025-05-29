package com.PruebaTecnica.PruebaTecnica_PedroMLopez.dto;

import java.time.LocalDateTime;

public class CamaDetalleDTO {
    private String estado;
    private String nombreHospital;
    private String nombreDependencia;
    private String etiqueta;
    private LocalDateTime fechaAlta;
    private LocalDateTime fechaBaja;

    public CamaDetalleDTO(String estado, String nombreHospital, String nombreDependencia, String etiqueta, LocalDateTime fechaAlta, LocalDateTime fechaBaja) {
        this.estado = estado;
        this.nombreHospital = nombreHospital;
        this.nombreDependencia = nombreDependencia;
        this.etiqueta = etiqueta;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
    }

    public CamaDetalleDTO() {

    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDateTime fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDateTime getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDateTime fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreHospital() {
        return nombreHospital;
    }

    public void setNombreHospital(String nombreHospital) {
        this.nombreHospital = nombreHospital;
    }

    public String getNombreDependencia() {
        return nombreDependencia;
    }

    public void setNombreDependencia(String nombreDependencia) {
        this.nombreDependencia = nombreDependencia;
    }
}
