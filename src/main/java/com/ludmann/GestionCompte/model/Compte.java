package com.ludmann.GestionCompte.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.ludmann.GestionCompte.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Compte implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({CustomJsonView.VueUtilisateur.class, CustomJsonView.VueCompte.class})
    private Integer id;

    @JsonView({CustomJsonView.VueUtilisateur.class, CustomJsonView.VueCompte.class})
    private String nom;

    @JsonView({CustomJsonView.VueUtilisateur.class, CustomJsonView.VueCompte.class})
    private double solde;

    @JsonView({CustomJsonView.VueCompte.class, CustomJsonView.VueUtilisateur.class})
    private double seuilAlerte;

    @JsonView({CustomJsonView.VueUtilisateur.class, CustomJsonView.VueCompte.class})
    @OneToMany(mappedBy = "compte")
    private List<Flux> listeFlux;

    @JsonView({CustomJsonView.VueCompte.class})
    @ManyToOne
    private Utilisateur utilisateur;

    public void calculNewSolde() {
        double somme = 0;

        for (Flux flux : this.getListeFlux()) {
            if (!flux.isPrisEnCompte()) {
                somme = somme + flux.getMontant();
                flux.setPrisEnCompte(true);
            }
        }
        setSolde(solde + somme);
    }

    public Integer getId() {
        return id;
    }

    public List<Flux> getListeFlux() {
        return listeFlux;
    }

    public void setListeFlux(List<Flux> listeFlux) {
        this.listeFlux = listeFlux;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public double getSeuilAlerte() {
        return seuilAlerte;
    }

    public void setSeuilAlerte(double seuilAlerte) {
        this.seuilAlerte = seuilAlerte;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
