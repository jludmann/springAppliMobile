package com.ludmann.GestionCompte.Controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ludmann.GestionCompte.dao.CompteDao;
import com.ludmann.GestionCompte.dao.UtilisateurDao;
import com.ludmann.GestionCompte.model.Compte;
import com.ludmann.GestionCompte.model.Utilisateur;
import com.ludmann.GestionCompte.security.JwtUtil;
import com.ludmann.GestionCompte.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
public class CompteController {

    CompteDao compteDao;
    UtilisateurDao utilisateurDao;
    JwtUtil jwtUtil;

    @Autowired
    CompteController(CompteDao compteDao, UtilisateurDao utilisateurDao, JwtUtil jwtUtil) {
        this.compteDao = compteDao;
        this.utilisateurDao = utilisateurDao;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/user/compte/{id}")
    @JsonView(value = CustomJsonView.VueCompte.class)
    public ResponseEntity<Compte> getCompte(@PathVariable int id) {


        Optional<Compte> compte = compteDao.findById(id);

        if (compte.isPresent()) {
            compte.get().calculNewSolde();
            compteDao.saveAndFlush(compte.get());
            return ResponseEntity.ok(compte.get());
        }
        else {
            return ResponseEntity.noContent().build();
        }

    }


    @GetMapping("/user/listeComptes")
    public ResponseEntity<List<Compte>> getComptes() {

        return ResponseEntity.ok(compteDao.findAll());
    }

    @PostMapping("/user/compte")
    public ResponseEntity<String> addCompte(@RequestBody Compte compte,
                                            @RequestHeader(value="Authorization") String authorization) {

        String token = authorization.substring(7);
        Integer idUtilisateur = jwtUtil.getTokenBody(token).get("id",Integer.class);
        ArrayList<String> listeNomRole = (ArrayList<String>)jwtUtil.getTokenBody(token).get("roles", ArrayList.class);

        //---si l'id de l'utilisateur n'a pas été rajouté dans le token---
        //Utilisateur user = utilisateurDao.trouverParLogin(jwtUtil.getTokenBody(token).getSubject()).get();
        //user.getId();

        if(compte.getId() == null) {
            Utilisateur connectedUser = utilisateurDao.findById(idUtilisateur).get();
            compte.setUtilisateur(connectedUser);
            utilisateurDao.saveAndFlush(connectedUser);
            compte = compteDao.saveAndFlush(compte);

            return ResponseEntity.created(
                    URI.create("/user/compte/" + compte.getId())
            ).build();
        } else {
            Optional<Compte> compteBdd = compteDao.findById(compte.getId());

            if (compteBdd.isPresent()){
                if(compteBdd.get().getUtilisateur().getId() == idUtilisateur || listeNomRole.contains("ROLE_ADMINISTRATEUR")) {
                    compteBdd.get().setNom(compte.getNom());
                    compteBdd.get().setSolde(compte.getSolde());
                    compteDao.save(compteBdd.get());
                    return ResponseEntity.ok().build();
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Vous n'avez pas les droits pour modifier ce compte");
                }
            } else {
                return ResponseEntity.badRequest().body("Ce compte n'existe pas ou a été supprimé");
            }
        }
    }

    @DeleteMapping("/admin/compte/{id}")
    public ResponseEntity<Integer> deleteCompte(@PathVariable int id) {

        if (compteDao.existsById(id)) {
            compteDao.deleteById(id);
            return ResponseEntity.ok().body(id);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}

