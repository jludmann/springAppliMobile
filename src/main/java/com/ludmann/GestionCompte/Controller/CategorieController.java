package com.ludmann.GestionCompte.Controller;

import com.ludmann.GestionCompte.dao.CategorieDao;
import com.ludmann.GestionCompte.model.Categorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
public class CategorieController {

    CategorieDao categorieDao;

    @Autowired
    CategorieController(CategorieDao categorieDao) {
        this.categorieDao = categorieDao;
    }

    @GetMapping("/categorie/{id}")
    public ResponseEntity<Categorie> getCategorie(@PathVariable int id) {


        Optional<Categorie> categorie = categorieDao.findById(id);

        if (categorie.isPresent()) {
            return ResponseEntity.ok(categorie.get());
        }
        else {
            return ResponseEntity.noContent().build();
        }

    }


    @GetMapping("/categories")
    public ResponseEntity<List<Categorie>> getCategorie() {

        return ResponseEntity.ok(categorieDao.findAll());
    }

    @PostMapping("/categorie")
    public ResponseEntity<String> addCategorie(@RequestBody Categorie categorie) {

        categorie = categorieDao.saveAndFlush(categorie);

        return ResponseEntity.created(URI.create("/categorie/" + categorie.getId())).build();

    }

    @DeleteMapping("/categorie/{id}")
    public ResponseEntity<Integer> deleteCategorie(@PathVariable int id) {

        if (categorieDao.existsById(id)) {
            categorieDao.deleteById(id);
            return ResponseEntity.ok().body(id);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}

