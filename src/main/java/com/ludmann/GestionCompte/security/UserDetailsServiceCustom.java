package com.ludmann.GestionCompte.security;

import com.ludmann.GestionCompte.dao.UtilisateurDao;
import com.ludmann.GestionCompte.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceCustom implements UserDetailsService {

    @Autowired
    UtilisateurDao utilisateurDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Utilisateur utilisateur = utilisateurDao.findByLogin(s).orElseThrow(() -> new UsernameNotFoundException(s + "inconnu"));


        return new UserDetailsCustom(utilisateur);
    }
}
