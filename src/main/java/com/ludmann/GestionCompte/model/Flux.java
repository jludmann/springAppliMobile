package com.ludmann.GestionCompte.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.ludmann.GestionCompte.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Flux implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({CustomJsonView.VueFlux.class, CustomJsonView.VueCompte.class, CustomJsonView.VueUtilisateur.class})
    private Integer id;

    @JsonView({CustomJsonView.VueFlux.class,CustomJsonView.VueUtilisateur.class, CustomJsonView.VueCompte.class})
    private double montant;

    @JsonView({CustomJsonView.VueFlux.class, CustomJsonView.VueCompte.class, CustomJsonView.VueUtilisateur.class})
    private String nom;

    @JsonView({CustomJsonView.VueFlux.class})
    @ManyToOne
    private Compte compte;

    @JsonView({CustomJsonView.VueFlux.class})
    @ManyToOne
    private Categorie categorie;

    private boolean prisEnCompte = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public boolean isPrisEnCompte() {
        return prisEnCompte;
    }

    public void setPrisEnCompte(boolean prisEnCompte) {
        this.prisEnCompte = prisEnCompte;
    }
}
