package com.PruebaTecnica.PruebaTecnica_PedroMLopez.service;

import com.PruebaTecnica.PruebaTecnica_PedroMLopez.dto.Response.CamaResponseDTO;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.dto.Response.DependenciaResponseDTO;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.exception.CamaNoEncontradaException;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.exception.EstadoInvalidoException;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.exception.hospital.DependenciaNoEncontradaException;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.exception.hospital.HospitalNoEncontradoException;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.model.*;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.repository.CamaRepository;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.repository.DependenciaRepository;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.repository.HospitalRepository;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.service.impl.HospitalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HospitalServiceImplTest {

    @Mock
    private HospitalRepository hospitalRepository;

    @Mock
    private DependenciaRepository dependenciaRepository;

    @Mock
    private CamaRepository camaRepository;

    @InjectMocks
    private HospitalServiceImpl hospitalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarCamasPorHospital_DatosCorrectos_DevuelveDTO() {
        when(hospitalRepository.existsById(1L)).thenReturn(true);

        Cama cama = new Cama();
        cama.setEtiqueta("CAMA-001");
        cama.setEstado(EstadoCama.LIBRE);
        cama.setFechaAltaEnHospital(LocalDateTime.now());

        when(camaRepository.findByHospitalId(1L)).thenReturn(List.of(cama));

        List<CamaResponseDTO> camas = hospitalService.listarCamasPorHospital(1L);

        assertEquals(1, camas.size());
        assertEquals("CAMA-001", camas.get(0).getEtiqueta());
    }


    @Test
    void listarDependenciasPorHospital_DatosCorrectos_DevuelveDTO() {
        when(hospitalRepository.existsById(2L)).thenReturn(true);

        Dependencia d = new Dependencia();
        d.setNombre("UCI");

        when(dependenciaRepository.findByHospitalId(2L)).thenReturn(List.of(d));

        List<DependenciaResponseDTO> deps = hospitalService.listarDependenciasPorHospital(2L);

        assertEquals(1, deps.size());
        assertEquals("UCI", deps.get(0).getNombre());
    }


    @Test
    void listarCamasPorDependenciaYHospital_DatosCorrectos_DevuelveDTO() {
        when(hospitalRepository.existsById(1L)).thenReturn(true);
        when(dependenciaRepository.existsById(2L)).thenReturn(true);

        Cama cama = new Cama();
        cama.setEtiqueta("CAMA-XYZ");
        cama.setEstado(EstadoCama.OCUPADA);

        when(camaRepository.findByDependenciaIdAndHospitalId(2L, 1L)).thenReturn(List.of(cama));

        List<CamaResponseDTO> camas = hospitalService.listarCamasPorDependenciaYHospital(1L, 2L);

        assertEquals(1, camas.size());
        assertEquals("CAMA-XYZ", camas.get(0).getEtiqueta());
    }


    @Test
    void asignarCamaHospital_DatosCorrectos_AsignaCama() {
        Hospital h = new Hospital();
        h.setId(1L);

        Dependencia d = new Dependencia();
        d.setId(1L);
        d.setNombre("UCI");

        Cama cama = new Cama();
        cama.setId(10L);
        cama.setEstado(EstadoCama.LIBRE);

        when(hospitalRepository.findById(1L)).thenReturn(Optional.of(h));
        when(dependenciaRepository.findByNombreAndHospitalId("UCI", 1L)).thenReturn(Optional.of(d));
        when(camaRepository.findById(10L)).thenReturn(Optional.of(cama));

        hospitalService.asignarCamaHospital(1L, 10L, "UCI");

        verify(camaRepository).save(cama);
        assertEquals(h, cama.getHospital());
        assertEquals(d, cama.getDependencia());
        assertEquals(EstadoCama.OCUPADA, cama.getEstado());
        assertNotNull(cama.getFechaAltaEnHospital());
    }


    @Test
    void eliminarCamaHospital_DatosCorrectos_EliminaCama() {
        Cama cama = new Cama();
        cama.setId(10L);
        cama.setEstado(EstadoCama.OCUPADA);
        cama.setHospital(new Hospital());
        cama.setDependencia(new Dependencia());

        when(camaRepository.findByIdAndHospitalId(10L, 1L)).thenReturn(cama);

        hospitalService.eliminarCamaHospital(1L, 10L);

        verify(camaRepository).save(cama);
        assertNull(cama.getHospital());
        assertNull(cama.getDependencia());
        assertEquals(EstadoCama.LIBRE, cama.getEstado());
        assertNotNull(cama.getFechaBajaEnHospital());
    }

    //Test Excepciones

    @Test
    void listarCamasPorHospital_HospitalNoExiste_LanzaExcepcion() {
        when(hospitalRepository.existsById(1L)).thenReturn(false);
        assertThrows(HospitalNoEncontradoException.class, () -> hospitalService.listarCamasPorHospital(1L));
    }

    @Test
    void listarDependenciasPorHospital_HospitalNoExiste_LanzaExcepcion() {
        when(hospitalRepository.existsById(2L)).thenReturn(false);
        assertThrows(HospitalNoEncontradoException.class, () -> hospitalService.listarDependenciasPorHospital(2L));
    }

    @Test
    void listarCamasPorDependenciaYHospital_NoExisteHospital_LanzaExcepcion() {
        when(hospitalRepository.existsById(1L)).thenReturn(false);
        assertThrows(HospitalNoEncontradoException.class, () ->
                hospitalService.listarCamasPorDependenciaYHospital(1L, 2L));
    }

    @Test
    void listarCamasPorDependenciaYHospital_NoExisteDependencia_LanzaExcepcion() {
        when(hospitalRepository.existsById(1L)).thenReturn(true);
        when(dependenciaRepository.existsById(2L)).thenReturn(false);
        assertThrows(DependenciaNoEncontradaException.class, () ->
                hospitalService.listarCamasPorDependenciaYHospital(1L, 2L));
    }

    @Test
    void asignarCamaHospital_CamaAsignada_LanzaExcepcion() {
        Hospital h = new Hospital();
        Dependencia d = new Dependencia();
        Cama cama = new Cama();
        cama.setEstado(EstadoCama.LIBRE);
        cama.setHospital(new Hospital());

        when(hospitalRepository.findById(1L)).thenReturn(Optional.of(h));
        when(dependenciaRepository.findByNombreAndHospitalId("UCI", 1L)).thenReturn(Optional.of(d));
        when(camaRepository.findById(10L)).thenReturn(Optional.of(cama));

        assertThrows(EstadoInvalidoException.class, () -> hospitalService.asignarCamaHospital(1L, 10L, "UCI"));
    }

    @Test
    void asignarCamaHospital_CamaNoLibre_LanzaExcepcion() {
        Hospital h = new Hospital();
        Dependencia d = new Dependencia();
        Cama cama = new Cama();
        cama.setEstado(EstadoCama.OCUPADA);
        cama.setHospital(null);

        when(hospitalRepository.findById(1L)).thenReturn(Optional.of(h));
        when(dependenciaRepository.findByNombreAndHospitalId("UCI", 1L)).thenReturn(Optional.of(d));
        when(camaRepository.findById(10L)).thenReturn(Optional.of(cama));

        assertThrows(EstadoInvalidoException.class, () -> hospitalService.asignarCamaHospital(1L, 10L, "UCI"));
    }

    @Test
    void eliminarCamaHospital_CamaNoExiste_LanzaExcepcion() {
        when(camaRepository.findByIdAndHospitalId(10L, 1L)).thenReturn(null);
        assertThrows(CamaNoEncontradaException.class, () -> hospitalService.eliminarCamaHospital(1L, 10L));
    }
}
