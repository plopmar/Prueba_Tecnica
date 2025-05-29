package com.PruebaTecnica.PruebaTecnica_PedroMLopez.repository;

import com.PruebaTecnica.PruebaTecnica_PedroMLopez.model.Cama;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CamaRepository extends JpaRepository<Cama, Long> {
    List<Cama> findByHospitalId(Long hospitalId);
    List<Cama> findByDependenciaIdAndHospitalId(Long dependenciaId, Long hospitalId);
    Cama findByIdAndHospitalId(Long id, Long hospitalId);
}
