package com.ludmann.GestionCompte.model;

import java.util.Date;

public class FluxMensuel extends Flux{

    private Date jourDuMois;
    private Date DateFin;

    public Date getJourDuMois() {
        return jourDuMois;
    }

    public void setJourDuMois(Date jourDuMois) {
        this.jourDuMois = jourDuMois;
    }

    public Date getDateFin() {
        return DateFin;
    }

    public void setDateFin(Date dateFin) {
        DateFin = dateFin;
    }
}
