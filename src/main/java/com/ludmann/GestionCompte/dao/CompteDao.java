package com.ludmann.GestionCompte.dao;

import com.ludmann.GestionCompte.model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompteDao extends JpaRepository<Compte, Integer> {
}
