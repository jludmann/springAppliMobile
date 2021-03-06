package com.ludmann.GestionCompte.dao;

import com.ludmann.GestionCompte.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieDao extends JpaRepository<Categorie, Integer> {
}
