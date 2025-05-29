package com.PruebaTecnica.PruebaTecnica_PedroMLopez.exception;

import com.PruebaTecnica.PruebaTecnica_PedroMLopez.dto.Response.CamaErrorResponse;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.exception.cama.CamaDuplicadaException;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.exception.hospital.DependenciaNoEncontradaException;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.exception.hospital.DependenciaNoPerteneceHospitalException;
import com.PruebaTecnica.PruebaTecnica_PedroMLopez.exception.hospital.HospitalNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CamaErrorResponse> handleIllegalArgumentException(IllegalArgumentException e){
        CamaErrorResponse response = new CamaErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(CamaDuplicadaException.class)
    public ResponseEntity<CamaErrorResponse> handleCamaDuplicadaException(CamaDuplicadaException e){
        CamaErrorResponse response = new CamaErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(response);
    }

    @ExceptionHandler(CamaNoEncontradaException.class)
    public ResponseEntity<CamaErrorResponse> handleCamaNoEncontradaException(CamaNoEncontradaException e){
        CamaErrorResponse response = new CamaErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(EstadoInvalidoException.class)
    public ResponseEntity<CamaErrorResponse> handleEstadoInvalidoException(EstadoInvalidoException e){
        CamaErrorResponse response = new CamaErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DependenciaNoEncontradaException.class)
    public ResponseEntity<CamaErrorResponse> handleDependenciaNoEncontradaException(DependenciaNoEncontradaException e){
        CamaErrorResponse response = new CamaErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(HospitalNoEncontradoException.class)
    public ResponseEntity<CamaErrorResponse> handleHospitalNoEncontradoException(HospitalNoEncontradoException e){
        CamaErrorResponse response = new CamaErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DependenciaNoPerteneceHospitalException.class)
    public ResponseEntity<CamaErrorResponse> handleDependenciaNoPerteneceHospitalException(DependenciaNoPerteneceHospitalException e){
        CamaErrorResponse response = new CamaErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


}
