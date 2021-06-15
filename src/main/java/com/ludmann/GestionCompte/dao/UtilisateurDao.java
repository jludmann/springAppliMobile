package com.ludmann.GestionCompte.dao;

import com.ludmann.GestionCompte.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurDao extends JpaRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByLogin(String s);

    @Query("FROM Utilisateur u JOIN FETCH u.listeRole WHERE login = :login")
    Optional<Utilisateur> trouverParLoginAvecRoles(@Param("login") String login);

    @Query( "FROM Utilisateur u " +
            "JOIN FETCH u.listeCompte n " +
            "WHERE login = :pseudo " +
            "ORDER BY n.id DESC")
    Optional<Utilisateur> trouverParLogin(@Param("pseudo") String pseudo);
}
