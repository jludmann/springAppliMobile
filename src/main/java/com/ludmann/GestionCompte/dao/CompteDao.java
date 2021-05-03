package com.ludmann.GestionCompte.dao;

import com.ludmann.GestionCompte.model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteDao extends JpaRepository<Compte, Integer> {
}
