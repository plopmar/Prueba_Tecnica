package com.PruebaTecnica.PruebaTecnica_PedroMLopez.service.impl;

import com.PruebaTecnica.PruebaTecnica_PedroMLopez.dto.Response.CamaResponseDTO;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.dto.Response.DependenciaResponseDTO;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.exception.CamaNoEncontradaException;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.exception.EstadoInvalidoException;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.exception.hospital.DependenciaNoEncontradaException;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.exception.hospital.HospitalNoEncontradoException;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.model.Cama;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.model.Dependencia;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.model.EstadoCama;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.model.Hospital;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.repository.CamaRepository;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.repository.DependenciaRepository;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.repository.HospitalRepository;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.service.interfaces.HospitalService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;
    private final DependenciaRepository dependenciaRepository;
    private final CamaRepository camaRepository;

    public HospitalServiceImpl(HospitalRepository hospitalRepository, DependenciaRepository dependenciaRepository, CamaRepository camaRepository) {
        this.hospitalRepository = hospitalRepository;
        this.dependenciaRepository = dependenciaRepository;
        this.camaRepository = camaRepository;
    }

    private LocalDateTime horaActual(){
        return LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }


    @Override
    public List<CamaResponseDTO> listarCamasPorHospital(Long idHospital) {
        if (!hospitalRepository.existsById(idHospital)) {
            throw new HospitalNoEncontradoException("Hospital no encontrado");
        }
        List<Cama> camas = camaRepository.findByHospitalId(idHospital);
        return camas.stream()
                .map(cama -> new CamaResponseDTO(cama.getEtiqueta(), cama.getEstado(), cama.getFechaAltaEnHospital())).toList();
    }

    @Override
    public List<DependenciaResponseDTO> listarDependenciasPorHospital(Long idHospital) {
        if (!hospitalRepository.existsById(idHospital)) {
            throw new HospitalNoEncontradoException("Hospital no encontrado");
        }

        List<Dependencia> dependencias = dependenciaRepository.findByHospitalId(idHospital);
        return dependencias.stream()
                .map(dependencia -> new DependenciaResponseDTO(dependencia.getNombre())).toList();


    }


    @Override
    public List<CamaResponseDTO> listarCamasPorDependenciaYHospital(Long idHospital, Long idDependencia) {

        boolean hospitalExiste = hospitalRepository.existsById(idHospital);
        boolean dependenciaExiste = dependenciaRepository.existsById(idDependencia);

        if(!hospitalExiste){
            throw new HospitalNoEncontradoException("Hospital no existe");
        }

        if(!dependenciaExiste){
            throw new DependenciaNoEncontradaException("Dependencia no existe");
        }


        List<Cama> camas = camaRepository.findByDependenciaIdAndHospitalId(idDependencia, idHospital);
        return camas.stream()
                .map(cama -> new CamaResponseDTO(cama.getEtiqueta(), cama.getEstado(), cama.getFechaAltaEnHospital())).toList();
    }

    @Transactional
    @Override
    public void asignarCamaHospital(Long idHospital, Long idCama, String nombreDependencia) {

        Hospital hospital = hospitalRepository.findById(idHospital)
                .orElseThrow(() -> new HospitalNoEncontradoException("Hospital no encontrado"));

        Dependencia dependencia = dependenciaRepository.findByNombreAndHospitalId(nombreDependencia, idHospital)
                .orElseThrow(() -> new DependenciaNoEncontradaException("Dependencia no encontrada"));

        Cama cama = camaRepository.findById(idCama)
                .orElseThrow(() -> new CamaNoEncontradaException("Cama no encontrada"));

        if(cama.getHospital() != null){
            throw new EstadoInvalidoException("La cama se encuentra asignada a un hospital y dependencia");
        }

        if (cama.getEstado() != EstadoCama.LIBRE){
            throw new EstadoInvalidoException("La cama no está en estado LIBRE, no puede ser asignada");
        }



        cama.setHospital(hospital);
        cama.setDependencia(dependencia);
        cama.setEstado(EstadoCama.OCUPADA);
        cama.setFechaAltaEnHospital(horaActual());
        cama.setFechaBajaEnHospital(null);

        camaRepository.save(cama);

    }

    @Override
    public void eliminarCamaHospital(Long idHospital, Long idCama) {

        Cama cama = camaRepository.findByIdAndHospitalId(idCama,idHospital);
        if(cama == null){
            throw new CamaNoEncontradaException("No se encontró la cama con id: " +idCama);
        }

        cama.setHospital(null);
        cama.setDependencia(null);
        cama.setEstado(EstadoCama.LIBRE);
        cama.setFechaBajaEnHospital(horaActual());
        camaRepository.save(cama);
    }


}
