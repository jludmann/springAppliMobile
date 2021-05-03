package com.ludmann.GestionCompte.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.ludmann.GestionCompte.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({CustomJsonView.VueCategorie.class, CustomJsonView.VueFlux.class})
    private int id;

    private int nom;

    @JsonIgnore
    @OneToMany(mappedBy = "categorie")
    private List<Flux> listeFlux;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNom() {
        return nom;
    }

    public void setNom(int nom) {
        this.nom = nom;
    }
}
