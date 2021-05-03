package com.ludmann.GestionCompte.model;

import java.util.Date;

public class FluxExceptionnel extends Flux{

    private Date dateFlux;

    public Date getDateFlux() {
        return dateFlux;
    }

    public void setDateFlux(Date dateFlux) {
        this.dateFlux = dateFlux;
    }
}
