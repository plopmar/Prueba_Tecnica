package com.PruebaTecnica.PruebaTecnica_PedroMLopez.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "dependencias")
public class Dependencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @OneToMany(mappedBy = "dependencia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cama> camas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Cama> getCamas() {
        return camas;
    }

    public void setCamas(List<Cama> camas) {
        this.camas = camas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }
}
