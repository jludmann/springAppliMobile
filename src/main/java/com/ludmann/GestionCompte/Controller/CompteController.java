package com.ludmann.GestionCompte.Controller;

import com.ludmann.GestionCompte.dao.CompteDao;
import com.ludmann.GestionCompte.model.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
public class CompteController {

    CompteDao compteDao;

    @Autowired
    CompteController(CompteDao compteDao) {
        this.compteDao = compteDao;
    }

    @GetMapping("/user/compte/{id}")
    public ResponseEntity<Compte> getCompte(@PathVariable int id) {


        Optional<Compte> compte = compteDao.findById(id);

        if (compte.isPresent()) {
            return ResponseEntity.ok(compte.get());
        }
        else {
            return ResponseEntity.noContent().build();
        }

    }


    @GetMapping("/user/allcompte")
    public ResponseEntity<List<Compte>> getCompte() {

        return ResponseEntity.ok(compteDao.findAll());
    }

    @PostMapping("/user/compte")
    public ResponseEntity<String> addCompte(@RequestBody Compte compte) {

        compte = compteDao.saveAndFlush(compte);

        return ResponseEntity.created(URI.create("/user/compte/" + compte.getId())).build();

    }

    @DeleteMapping("/user/compte/{id}")
    public ResponseEntity<Integer> deleteCompte(@PathVariable int id) {

        if (compteDao.existsById(id)) {
            compteDao.deleteById(id);
            return ResponseEntity.ok().body(id);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}

