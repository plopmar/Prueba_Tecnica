package com.PruebaTecnica.PruebaTecnica_PedroMLopez.dto.Response;

public class CamaErrorResponse {

    private String message;

    public CamaErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
