package com.ludmann.GestionCompte.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.ludmann.GestionCompte.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({CustomJsonView.VueUtilisateur.class, CustomJsonView.VueCompte.class})
    private int id;

    @Column(nullable = false, unique = true)
    @JsonView({CustomJsonView.VueUtilisateur.class, CustomJsonView.VueCompte.class})
    private String login;

    @Column(nullable = false)
    private String password;

    private String nom;
    private String prenom;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonView(CustomJsonView.VueUtilisateur.class)
    @JoinTable(
            name = "utilisateur_role",
            joinColumns = @JoinColumn(name = "id_utilisateur"),
            inverseJoinColumns = @JoinColumn(name = "id_role")
    )
    private List<Role> listeRole;

    @JsonIgnore
    @OneToMany(mappedBy = "utilisateur")
    private List<Compte> listeCompte;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public List<Role> getListeRole() {
        return listeRole;
    }

    public void setListeRole(List<Role> listeRole) {
        this.listeRole = listeRole;
    }

    public List<Compte> getListeCompte() {
        return listeCompte;
    }

    public void setListeCompte(List<Compte> listeCompte) {
        this.listeCompte = listeCompte;
    }
}
