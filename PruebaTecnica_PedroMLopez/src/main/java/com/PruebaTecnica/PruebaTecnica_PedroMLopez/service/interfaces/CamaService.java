package com.PruebaTecnica.PruebaTecnica_PedroMLopez.service.interfaces;

import com.PruebaTecnica.PruebaTecnica_PedroMLopez.dto.CamaDetalleDTO;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.model.EstadoCama;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public interface CamaService {

    CamaDetalleDTO obtenerCama(Long idCama);
    void crearCama(Long idCama);
    void actualizarEstadoCama(Long idCama, EstadoCama nuevoEstado);
    void eliminarCama(Long idCama);
}
