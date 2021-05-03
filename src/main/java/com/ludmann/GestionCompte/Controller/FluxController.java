package com.ludmann.GestionCompte.Controller;

import com.ludmann.GestionCompte.dao.FluxDao;
import com.ludmann.GestionCompte.model.Flux;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
public class FluxController {

    FluxDao fluxDao;

    @Autowired
    FluxController(FluxDao fluxDao) {
        this.fluxDao = fluxDao;
    }

    @GetMapping("/user/flux/{id}")
    public ResponseEntity<Flux> getFlux(@PathVariable int id) {


        Optional<Flux> flux = fluxDao.findById(id);

        if (flux.isPresent()) {
            return ResponseEntity.ok(flux.get());
        }
        else {
            return ResponseEntity.noContent().build();
        }

    }


    @GetMapping("/user/allflux")
    public ResponseEntity<List<Flux>> getFlux() {

        return ResponseEntity.ok(fluxDao.findAll());
    }

    @PostMapping("/user/flux")
    public ResponseEntity<String> addFlux(@RequestBody Flux flux) {

        flux = fluxDao.saveAndFlush(flux);

        return ResponseEntity.created(URI.create("/user/flux/" + flux.getId())).build();

    }

    @DeleteMapping("/user/flux/{id}")
    public ResponseEntity<Integer> deleteFlux(@PathVariable int id) {

        if (fluxDao.existsById(id)) {
            fluxDao.deleteById(id);
            return ResponseEntity.ok().body(id);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}

