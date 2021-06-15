package com.ludmann.GestionCompte.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.ludmann.GestionCompte.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class FluxMensuel extends Flux{

    @JsonView({CustomJsonView.VueFlux.class, CustomJsonView.VueUtilisateur.class, CustomJsonView.VueCompte.class})
    private Date jourDuMois;

    @JsonView({CustomJsonView.VueFlux.class, CustomJsonView.VueUtilisateur.class, CustomJsonView.VueCompte.class})
    private Date dateFin;

    public Date getJourDuMois() {
        return jourDuMois;
    }

    public void setJourDuMois(Date jourDuMois) {
        this.jourDuMois = jourDuMois;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        dateFin = dateFin;
    }
}
