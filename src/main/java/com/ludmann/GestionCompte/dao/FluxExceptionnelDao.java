package com.ludmann.GestionCompte.dao;

import com.ludmann.GestionCompte.model.Flux;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FluxExceptionnelDao extends JpaRepository<Flux, Integer> {
}
