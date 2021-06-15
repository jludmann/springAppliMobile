package com.ludmann.GestionCompte.security;

import com.ludmann.GestionCompte.dao.UtilisateurDao;
import com.ludmann.GestionCompte.model.Utilisateur;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceCustom implements UserDetailsService {

    private final UtilisateurDao utilisateurDao;

    public UserDetailsServiceCustom(UtilisateurDao utilisateurDao) {
        this.utilisateurDao = utilisateurDao;
    }

    @Override
    public UserDetailsCustom loadUserByUsername(String pseudoSaisi) throws UsernameNotFoundException {

        Utilisateur utilisateur = utilisateurDao
                .trouverParLoginAvecRoles(pseudoSaisi)
                .orElseThrow(() -> new UsernameNotFoundException(pseudoSaisi + " inconnu"));

        return new UserDetailsCustom(utilisateur);
    }
}
