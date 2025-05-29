package com.PruebaTecnica.PruebaTecnica_PedroMLopez.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "camas")
public class Cama {

    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private String etiqueta;

    @Enumerated(EnumType.STRING)
    private EstadoCama estado;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @ManyToOne
    @JoinColumn(name = "dependencia_id")
    private Dependencia dependencia;

    private LocalDateTime fechaAlta;

    private LocalDateTime fechaBaja;

    private LocalDateTime fechaAltaEnHospital;

    private LocalDateTime fechaBajaEnHospital;

    public LocalDateTime getFechaAltaEnHospital() {
        return fechaAltaEnHospital;
    }

    public void setFechaAltaEnHospital(LocalDateTime fechaAltaEnHospital) {
        this.fechaAltaEnHospital = fechaAltaEnHospital;
    }

    public LocalDateTime getFechaBajaEnHospital() {
        return fechaBajaEnHospital;
    }

    public void setFechaBajaEnHospital(LocalDateTime fechaBajaEnHospital) {
        this.fechaBajaEnHospital = fechaBajaEnHospital;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public EstadoCama getEstado() {
        return estado;
    }

    public void setEstado(EstadoCama estado) {
        this.estado = estado;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Dependencia getDependencia() {
        return dependencia;
    }

    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }
}
