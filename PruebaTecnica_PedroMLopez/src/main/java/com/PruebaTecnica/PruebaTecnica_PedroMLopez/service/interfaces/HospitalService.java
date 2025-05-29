package com.PruebaTecnica.PruebaTecnica_PedroMLopez.service.interfaces;


import com.PruebaTecnica.PruebaTecnica_PedroMLopez.dto.Response.CamaResponseDTO;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.dto.Response.DependenciaResponseDTO;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@ComponentScan
public interface HospitalService {

    List<CamaResponseDTO> listarCamasPorHospital (Long idHospital);
    List<DependenciaResponseDTO> listarDependenciasPorHospital (Long idHospital);
    List<CamaResponseDTO> listarCamasPorDependenciaYHospital (Long idHospital, Long idDependencia);
    void asignarCamaHospital(Long idHospital, Long idCama, String nombreDependencia);
    void eliminarCamaHospital(Long idHospital, Long idCama);

}
