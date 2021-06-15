package com.ludmann.GestionCompte.model;



import com.fasterxml.jackson.annotation.JsonView;
import com.ludmann.GestionCompte.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="role")
@EntityListeners(AuditingEntityListener.class)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonView(CustomJsonView.VueUtilisateur.class)
    private String nom;

    @ManyToMany(mappedBy = "listeRole")
    List<Utilisateur> listeUtilisateur;

    public int getId() {
        return id;
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
}
