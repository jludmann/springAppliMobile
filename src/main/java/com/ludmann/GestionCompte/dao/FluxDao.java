package com.ludmann.GestionCompte.dao;

import com.ludmann.GestionCompte.model.Flux;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FluxDao extends JpaRepository<Flux, Integer> {
}
