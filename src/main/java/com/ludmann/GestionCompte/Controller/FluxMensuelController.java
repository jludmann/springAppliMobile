package com.ludmann.GestionCompte.Controller;

import com.ludmann.GestionCompte.dao.FluxMensuelDao;
import com.ludmann.GestionCompte.model.Flux;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
public class FluxMensuelController {

    FluxMensuelDao fluxMensuelDao;

    @Autowired
    FluxMensuelController(FluxMensuelDao fluxMensuelDao) {
        this.fluxMensuelDao = fluxMensuelDao;
    }

    @GetMapping("/user/fluxMensuel/{id}")
    public ResponseEntity<Flux> getFlux(@PathVariable int id) {


        Optional<Flux> flux = fluxMensuelDao.findById(id);

        if (flux.isPresent()) {
            return ResponseEntity.ok(flux.get());
        }
        else {
            return ResponseEntity.noContent().build();
        }

    }


    @GetMapping("/listeFluxMensuel")
    public ResponseEntity<List<Flux>> getFlux() {

        return ResponseEntity.ok(fluxMensuelDao.findAll());
    }

    @PostMapping("/fluxMensuel")
    public ResponseEntity<String> addFlux(@RequestBody Flux flux) {

        flux = fluxMensuelDao.saveAndFlush(flux);

        return ResponseEntity.created(URI.create("/fluxMensuel/" + flux.getId())).build();

    }

    @DeleteMapping("/fluxMensuel/{id}")
    public ResponseEntity<Integer> deleteFlux(@PathVariable int id) {

        if (fluxMensuelDao.existsById(id)) {
            fluxMensuelDao.deleteById(id);
            return ResponseEntity.ok().body(id);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}

