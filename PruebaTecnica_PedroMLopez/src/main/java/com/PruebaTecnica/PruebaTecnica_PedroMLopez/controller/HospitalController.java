package com.PruebaTecnica.PruebaTecnica_PedroMLopez.controller;


import com.PruebaTecnica.PruebaTecnica_PedroMLopez.dto.DependenciaDTO;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.dto.Response.CamaResponseDTO;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.dto.Response.DependenciaResponseDTO;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.service.interfaces.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospitales")
@Validated
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @Operation(summary = "Listar todas las camas de un hospital", description = "Devuelve lista con etiqueta identificadora, estado de la cama y fecha de alta en hospital")
    @GetMapping("/{idHospital}/camas")
    public ResponseEntity<List<CamaResponseDTO>> listarCamasPorHospital(@PathVariable Long idHospital){
        return ResponseEntity.ok(hospitalService.listarCamasPorHospital(idHospital));
    }

    @Operation(summary = "Listar todas las dependencias de un hospital")
    @GetMapping("/{idHospital}/dependencias")
    public ResponseEntity<List<DependenciaResponseDTO>> listarDependenciasPorHospital(@PathVariable Long idHospital){
        return ResponseEntity.ok(hospitalService.listarDependenciasPorHospital(idHospital));
    }

    @Operation(summary = "Listar todas las camas por dependencia de un hospital", description = "Muestra todas las camas dentro de una dependencia específica de un hospital determinado")
    @GetMapping("/{idHospital}/{idDependencia}/camas")
    public ResponseEntity<List<CamaResponseDTO>> listarCamasPorDependencia(@PathVariable Long idHospital,@PathVariable Long idDependencia){
        return ResponseEntity.ok(hospitalService.listarCamasPorDependenciaYHospital(idHospital, idDependencia));
    }

    @Operation(summary = "Registrar una cama en un hospital y dependencia")
    @PutMapping("/{idHospital}/camas/{idCama}")
    public ResponseEntity<Void> asignarCamaAHospital(@PathVariable Long idHospital, @PathVariable Long idCama, @RequestBody @Valid DependenciaDTO dependenciaDTO){
        hospitalService.asignarCamaHospital(idHospital,idCama,dependenciaDTO.getNombre());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Eliminar una cama de un hospital")
    @DeleteMapping("/{idHospital}/camas/{idCama}")
    public ResponseEntity<Void> eliminarCamaDeHospital(@PathVariable Long idHospital, @PathVariable Long idCama){
        hospitalService.eliminarCamaHospital(idHospital, idCama);
        return ResponseEntity.noContent().build();
    }





}
