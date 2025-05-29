package com.PruebaTecnica.PruebaTecnica_PedroMLopez.repository;

import com.PruebaTecnica.PruebaTecnica_PedroMLopez.model.Dependencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DependenciaRepository extends JpaRepository<Dependencia, Long> {
    List<Dependencia> findByHospitalId(Long hospitalId);
    Optional<Dependencia> findByNombreAndHospitalId(String nombre, Long hospitalId);

}
