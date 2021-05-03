package com.ludmann.GestionCompte.Controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ludmann.GestionCompte.view.CustomJsonView;
import com.ludmann.GestionCompte.dao.UtilisateurDao;
import com.ludmann.GestionCompte.model.Role;
import com.ludmann.GestionCompte.model.Utilisateur;
import com.ludmann.GestionCompte.security.JwtUtil;
import com.ludmann.GestionCompte.security.UserDetailsServiceCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
public class UtilisateurController {

    UtilisateurDao utilisateurDao;
    JwtUtil jwtUtil;
    AuthenticationManager authenticationManager;
    UserDetailsServiceCustom userDetailsServiceCustom;
    PasswordEncoder passwordEncoder;


    @Autowired
    UtilisateurController(UtilisateurDao utilisateurDao,
                          JwtUtil jwtUtil,
                          AuthenticationManager authenticationManager,
                          UserDetailsServiceCustom userDetailsServiceCustom,
                          PasswordEncoder passwordEncoder) {

        this.utilisateurDao = utilisateurDao;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsServiceCustom = userDetailsServiceCustom;
        this.passwordEncoder = passwordEncoder;

    }

    @PostMapping("/authentification")
    public ResponseEntity<String> authentification(@RequestBody Utilisateur utilisateur) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            utilisateur.getLogin(), utilisateur.getPassword()
                    )
            );
        }
        catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Login ou mot de passe inconnu");
        }

        UserDetails userDetails = this.userDetailsServiceCustom.loadUserByUsername(utilisateur.getLogin());

        return ResponseEntity.ok(jwtUtil.generateToken(userDetails));

    }

    @PostMapping("/inscription")
    public ResponseEntity<String> inscription(@RequestBody Utilisateur utilisateur) {

        Optional<Utilisateur> utilisateurDoublon = utilisateurDao.findByLogin(utilisateur.getLogin());

        if (utilisateurDoublon.isPresent()) {
            return ResponseEntity.badRequest().body("Ce login est déjà utilisé");
        }
        else {
            utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));

            Role roleUtilisateur = new Role();
            roleUtilisateur.setId(2);

            utilisateur.getListeRole().add(roleUtilisateur);

            utilisateurDao.saveAndFlush(utilisateur);

            return ResponseEntity.ok(Integer.toString(utilisateur.getId()));
        }

    }

    @JsonView(CustomJsonView.VueUtilisateur.class)
    @GetMapping("/user/utilisateur/{id}")
    public ResponseEntity<Utilisateur> getUtilisateur(@PathVariable int id) {

        Optional<Utilisateur> utilisateur = utilisateurDao.findById(id);

        if (utilisateur.isPresent()) {
            return ResponseEntity.ok(utilisateur.get());
        } else {
            return ResponseEntity.noContent().build();
        }

    }

    @JsonView(CustomJsonView.VueUtilisateur.class)
    @GetMapping("/user/utilisateurs")
    public ResponseEntity<List<Utilisateur>> getUtilisateur() {

        return ResponseEntity.ok(utilisateurDao.findAll());
    }


    @DeleteMapping("/admin/utilisateur/{id}")
    public ResponseEntity<Integer> deleteUtilisateur(@PathVariable int id) {

        if (utilisateurDao.existsById(id)) {
            utilisateurDao.deleteById(id);
            return ResponseEntity.ok().body(id);
        }
        else {
            return ResponseEntity.noContent().build();
        }

    }

}
