package com.ludmann.GestionCompte.dao;

import com.ludmann.GestionCompte.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieDao extends JpaRepository<Categorie, Integer> {
}
