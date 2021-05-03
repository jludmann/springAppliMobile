package com.ludmann.GestionCompte.dao;

import com.ludmann.GestionCompte.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurDao extends JpaRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByLogin(String s);
}
