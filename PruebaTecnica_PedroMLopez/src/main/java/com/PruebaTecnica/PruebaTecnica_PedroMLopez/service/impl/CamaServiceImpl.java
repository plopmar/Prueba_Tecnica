package com.PruebaTecnica.PruebaTecnica_PedroMLopez.service.impl;

import com.PruebaTecnica.PruebaTecnica_PedroMLopez.dto.CamaDetalleDTO;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.exception.cama.CamaDuplicadaException;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.exception.CamaNoEncontradaException;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.exception.EstadoInvalidoException;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.model.Cama;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.model.EstadoCama;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.repository.CamaRepository;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.service.interfaces.CamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class CamaServiceImpl implements CamaService {

    private final CamaRepository camaRepository;

    @Autowired
    public CamaServiceImpl(CamaRepository camaRepository) {
        this.camaRepository = camaRepository;
    }


    @Override
    public CamaDetalleDTO obtenerCama(Long idCama) {

        Cama cama = camaRepository.findById(idCama)
                .orElseThrow(() -> new CamaNoEncontradaException("Cama no encontrada con ID: " + idCama));

        CamaDetalleDTO dto = new CamaDetalleDTO();
        dto.setEtiqueta(cama.getEtiqueta());
        dto.setEstado(cama.getEstado().name());
        dto.setFechaAlta(cama.getFechaAlta());
        dto.setFechaBaja(cama.getFechaBaja());
        if (cama.getHospital() != null) {
            dto.setNombreHospital(cama.getHospital().getNombre());
        }

        if (cama.getDependencia() != null) {
            dto.setNombreDependencia(cama.getDependencia().getNombre());
        }

        return dto;
    }

    @Override
    public void crearCama(Long idCama) {

        if(idCama == null || idCama<= 0){
            throw new IllegalArgumentException("El ID de la cama debe ser un número positivo");
        }

        if(camaRepository.existsById(idCama)){
            throw new CamaDuplicadaException("Ya existe una cama con este ID: " +idCama);
        }

        String nuevaEtiqueta = generarNuevaEtiqueta();

        Cama cama = new Cama();
        cama.setId(idCama);
        cama.setEtiqueta(nuevaEtiqueta);
        cama.setEstado(EstadoCama.LIBRE);
        cama.setFechaAlta(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        camaRepository.save(cama);
    }

    @Override
    public void actualizarEstadoCama(Long idCama, EstadoCama nuevoEstado) {
        Cama cama = camaRepository.findById(idCama)
                .orElseThrow(() -> new CamaNoEncontradaException("Cama no encontrada con id: " + idCama));

        if (cama.getHospital() != null){
            throw new EstadoInvalidoException("La cama con ID: " + idCama + " está asignada a un hospital y no puede cambiar su estado");
        }
        if (!cambioValido(cama.getEstado(), nuevoEstado)){
            throw new EstadoInvalidoException("Cambio de estado no válido de " + cama.getEstado() + " a " + nuevoEstado);
        }
        cama.setEstado(nuevoEstado);
        camaRepository.save(cama);

    }

    @Override
    public void eliminarCama(Long idCama) {
        Cama cama = camaRepository.findById(idCama)
                .orElseThrow(() -> new CamaNoEncontradaException("No se puede eliminar la cama con ID " + idCama + ", no existe"));

        cama.setEstado(EstadoCama.BAJA);
        cama.setFechaBaja(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        if(cama.getHospital() != null && cama.getDependencia() != null){
            cama.setFechaBajaEnHospital(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            cama.setHospital(null);
            cama.setDependencia(null);
        }

        camaRepository.save(cama);
    }


    private boolean cambioValido(EstadoCama actual, EstadoCama nuevo) {
        return switch (actual) {
            case LIBRE -> nuevo == EstadoCama.OCUPADA
                    || nuevo == EstadoCama.AVERIADA;

            case AVERIADA -> nuevo == EstadoCama.EN_REPARACION;

            case EN_REPARACION -> nuevo == EstadoCama.LIBRE;

            case OCUPADA -> nuevo == EstadoCama.LIBRE;

            case BAJA -> false;
        };
    }


    private String generarNuevaEtiqueta(){
        return "CAMA-" + UUID.randomUUID().toString().substring(0,4).toUpperCase();
    }
}
