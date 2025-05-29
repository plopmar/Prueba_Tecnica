package com.PruebaTecnica.PruebaTecnica_PedroMLopez.service;

import com.PruebaTecnica.PruebaTecnica_PedroMLopez.dto.CamaDetalleDTO;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.exception.CamaNoEncontradaException;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.exception.EstadoInvalidoException;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.exception.cama.CamaDuplicadaException;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.model.Cama;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.model.Dependencia;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.model.EstadoCama;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.model.Hospital;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.repository.CamaRepository;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.service.impl.CamaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CamaServiceImplTest {

    @Mock
    private CamaRepository camaRepository;

    @InjectMocks
    private CamaServiceImpl camaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerCama_DatosCorrectos_DevuelveDTO() {
        Cama cama = new Cama();
        cama.setEtiqueta("CAMA-1234");
        cama.setEstado(EstadoCama.LIBRE);
        cama.setFechaAlta(LocalDateTime.now());

        Hospital hospital = new Hospital();
        hospital.setNombre("Hospital Central");
        cama.setHospital(hospital);

        Dependencia dependencia = new Dependencia();
        dependencia.setNombre("Urgencias");
        cama.setDependencia(dependencia);

        when(camaRepository.findById(1L)).thenReturn(Optional.of(cama));

        CamaDetalleDTO dto = camaService.obtenerCama(1L);

        assertEquals("CAMA-1234", dto.getEtiqueta());
        assertEquals("LIBRE", dto.getEstado());
        assertEquals("Hospital Central", dto.getNombreHospital());
        assertEquals("Urgencias", dto.getNombreDependencia());
    }


    @Test
    void crearCama_IdCorrecto_GuardarCama() {
        when(camaRepository.existsById(5L)).thenReturn(false);

        camaService.crearCama(5L);

        ArgumentCaptor<Cama> captor = ArgumentCaptor.forClass(Cama.class);
        verify(camaRepository).save(captor.capture());

        Cama creada = captor.getValue();
        assertEquals(5L, creada.getId());
        assertEquals(EstadoCama.LIBRE, creada.getEstado());
        assertNotNull(creada.getEtiqueta());
    }

    @Test
    void actualizarEstadoCama_TransicionValida_CambiaEstado() {
        Cama cama = new Cama();
        cama.setEstado(EstadoCama.LIBRE);
        cama.setHospital(null);

        when(camaRepository.findById(1L)).thenReturn(Optional.of(cama));

        camaService.actualizarEstadoCama(1L, EstadoCama.OCUPADA);

        verify(camaRepository).save(cama);
        assertEquals(EstadoCama.OCUPADA, cama.getEstado());
    }


    @Test
    void eliminarCama_CamaExistente_BorradoLogico() {
        Cama cama = new Cama();
        cama.setEstado(EstadoCama.LIBRE);
        cama.setHospital(new Hospital());
        cama.setDependencia(new Dependencia());

        when(camaRepository.findById(1L)).thenReturn(Optional.of(cama));

        camaService.eliminarCama(1L);

        verify(camaRepository).save(cama);
        assertEquals(EstadoCama.BAJA, cama.getEstado());
        assertNull(cama.getHospital());
        assertNull(cama.getDependencia());
        assertNotNull(cama.getFechaBaja());
        assertNotNull(cama.getFechaBajaEnHospital());
    }

    //Test Excepciones

    @Test
    void obtenerCama_CamaNoExiste_LanzaExcepcion() {
        when(camaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CamaNoEncontradaException.class, () -> camaService.obtenerCama(1L));
    }

    @Test
    void crearCama_CamaExistente_LanzaExcepcion() {
        when(camaRepository.existsById(1L)).thenReturn(true);
        assertThrows(CamaDuplicadaException.class, () -> camaService.crearCama(1L));
    }

    @Test
    void actualizarEstadoCama_HospitalAsignado_LanzaExcepcion() {
        Cama cama = new Cama();
        cama.setEstado(EstadoCama.LIBRE);
        cama.setHospital(new Hospital());

        when(camaRepository.findById(1L)).thenReturn(Optional.of(cama));

        assertThrows(EstadoInvalidoException.class, () -> camaService.actualizarEstadoCama(1L, EstadoCama.OCUPADA));
    }

    @Test
    void actualizarEstadoCama_TransicionNoValida_LanzaExcepcion() {
        Cama cama = new Cama();
        cama.setEstado(EstadoCama.LIBRE);
        cama.setHospital(null);

        when(camaRepository.findById(1L)).thenReturn(Optional.of(cama));

        assertThrows(EstadoInvalidoException.class, () -> camaService.actualizarEstadoCama(1L, EstadoCama.EN_REPARACION));
    }

    @Test
    void eliminarCama_CamaNoExistente_LanzaExcepcion() {
        when(camaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CamaNoEncontradaException.class, () -> camaService.eliminarCama(1L));
    }

}
