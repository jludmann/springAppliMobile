package com.ludmann.GestionCompte.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.ludmann.GestionCompte.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class FluxExceptionnel extends Flux{

    @JsonView({CustomJsonView.VueFlux.class, CustomJsonView.VueUtilisateur.class})
    private Date dateFlux;

    public Date getDateFlux() {
        return dateFlux;
    }

    public void setDateFlux(Date dateFlux) {
        this.dateFlux = dateFlux;
    }
}
