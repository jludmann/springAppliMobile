package com.ludmann.GestionCompte.Controller;

import com.ludmann.GestionCompte.dao.FluxExceptionnelDao;
import com.ludmann.GestionCompte.model.Flux;
import com.ludmann.GestionCompte.model.FluxExceptionnel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
public class FluxExceptionnelController {

    FluxExceptionnelDao fluxExceptionnelDao;

    @Autowired
    FluxExceptionnelController(FluxExceptionnelDao fluxExceptionnelDao) {
        this.fluxExceptionnelDao = fluxExceptionnelDao;
    }

    @GetMapping("/user/fluxExceptionnel/{id}")
    public ResponseEntity<Flux> getFlux(@PathVariable int id) {


        Optional<Flux> flux = fluxExceptionnelDao.findById(id);

        if (flux.isPresent()) {
            return ResponseEntity.ok(flux.get());
        }
        else {
            return ResponseEntity.noContent().build();
        }

    }


    @GetMapping("/user/listeFluxExceptionnel")
    public ResponseEntity<List<Flux>> getFlux() {

        return ResponseEntity.ok(fluxExceptionnelDao.findAll());
    }

    @PostMapping("/user/fluxExceptionnel")
    public ResponseEntity<String> addFlux(@RequestBody FluxExceptionnel flux) {

        flux = fluxExceptionnelDao.saveAndFlush(flux);

        return ResponseEntity.created(URI.create("/fluxExceptionnel/" + flux.getId())).build();

    }

    @DeleteMapping("/user/fluxExceptionnel/{id}")
    public ResponseEntity<Integer> deleteFlux(@PathVariable int id) {

        if (fluxExceptionnelDao.existsById(id)) {
            fluxExceptionnelDao.deleteById(id);
            return ResponseEntity.ok().body(id);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}

