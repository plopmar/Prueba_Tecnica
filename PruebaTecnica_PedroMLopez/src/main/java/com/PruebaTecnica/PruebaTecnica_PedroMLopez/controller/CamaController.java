package com.PruebaTecnica.PruebaTecnica_PedroMLopez.controller;


import com.PruebaTecnica.PruebaTecnica_PedroMLopez.dto.CamaDetalleDTO;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.dto.EstadoCamaDTO;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.service.interfaces.CamaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/camas")
public class CamaController {

    private final CamaService camaService;

    public CamaController(CamaService camaService) {
        this.camaService = camaService;
    }

    @Operation(summary = "Obtener información de una cama por su ID", description = "Devuelve estado de la cama, si pertenece a algun hospital y dependencia, etiqueta que lo identifica y las fechas de alta y baja globales")
    @GetMapping("/{idCama}")
    public ResponseEntity<CamaDetalleDTO> obtenerCama(@PathVariable Long idCama){
        return ResponseEntity.ok(camaService.obtenerCama(idCama));
    }

    @Operation(summary = "Registrar nueva cama con valores por defecto", description = "Crea una cama con valores por defecto, fecha de alta y etiqueta identificadora aleatoria. No se asigna a hospital ni dependencia")
    @PostMapping("/{idCama}")
    public ResponseEntity<Void> crearCama(@PathVariable Long idCama){
        camaService.crearCama(idCama);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Actualizar estado de una cama", description = "Modifica el estado de una cama mientras no esté asignada a un hospital")
    @PutMapping("/{idCama}")
    public ResponseEntity<Void> actualizarEstado(@PathVariable Long idCama, @RequestBody EstadoCamaDTO estadoCamaDTO){
        camaService.actualizarEstadoCama(idCama, estadoCamaDTO.getEstadoCama());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Dar de baja una cama")
    @DeleteMapping("/{idCama}")
    public ResponseEntity<Void> eliminarCama(@PathVariable Long idCama){
        camaService.eliminarCama(idCama);
        return ResponseEntity.noContent().build();
    }
}
