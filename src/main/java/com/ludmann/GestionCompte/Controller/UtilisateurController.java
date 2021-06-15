package com.ludmann.GestionCompte.Controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ludmann.GestionCompte.dao.CompteDao;
import com.ludmann.GestionCompte.model.Compte;
import com.ludmann.GestionCompte.model.Role;
import com.ludmann.GestionCompte.security.JwtUtil;
import com.ludmann.GestionCompte.security.UserDetailsCustom;
import com.ludmann.GestionCompte.security.UserDetailsServiceCustom;
import com.ludmann.GestionCompte.view.CustomJsonView;
import com.ludmann.GestionCompte.dao.UtilisateurDao;
import com.ludmann.GestionCompte.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
public class UtilisateurController {

    private AuthenticationManager authenticationManager;
    private UserDetailsServiceCustom userDetailsServiceCustom;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    private UtilisateurDao utilisateurDao;
    private CompteDao compteDao;


    @Autowired
    UtilisateurController(UtilisateurDao utilisateurDao,
                          JwtUtil jwtUtil,
                          AuthenticationManager authenticationManager,
                          UserDetailsServiceCustom userDetailsServiceCustom,
                          PasswordEncoder passwordEncoder,
                          CompteDao compteDao) {

        this.utilisateurDao = utilisateurDao;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsServiceCustom = userDetailsServiceCustom;
        this.passwordEncoder = passwordEncoder;
        this.compteDao = compteDao;
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
            return ResponseEntity.badRequest().body("Login ou mot de passe incorrect !");
        }

        UserDetailsCustom userDetails = this.userDetailsServiceCustom.loadUserByUsername(utilisateur.getLogin());

        return ResponseEntity.ok(jwtUtil.generateToken(userDetails));

    }

    @PostMapping("/inscription")
    public ResponseEntity<String> inscription(@RequestBody Utilisateur utilisateur) {

        Optional<Utilisateur> utilisateurDoublon = utilisateurDao.findByLogin(utilisateur.getLogin());

        if (utilisateurDoublon.isPresent()) {
            return ResponseEntity.badRequest().body("Ce login est déjà utilisé");
        } else {
            utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));

            Role roleUtilisateur = new Role();
            roleUtilisateur.setId(1);

            utilisateur.getListeRole().add(roleUtilisateur);

            utilisateurDao.saveAndFlush(utilisateur);

            return ResponseEntity.ok(Integer.toString(utilisateur.getId()));
        }
    }

    @JsonView(CustomJsonView.VueUtilisateur.class)
    @GetMapping("/user/utilisateur-connecte")
    public ResponseEntity<Utilisateur> getInformationUtilisateurConnecte(
            @RequestHeader(value="Authorization") String authorization){
        //la valeur du champs authorization est extrait de l'entête de la requête

        //On supprime la partie "Bearer " de la valeur de l'authorization
        String token = authorization.substring(7);

        //on extrait l'information souhaitée du token
        String username = jwtUtil.getTokenBody(token).getSubject();

        Optional<Utilisateur> utilisateur = utilisateurDao.trouverParLogin(username);

        if(utilisateur.isPresent()) {
            for (Compte compte : utilisateur.get().getListeCompte()) {
                compte.calculNewSolde();
                compteDao.saveAndFlush(compte);
            }
            return ResponseEntity.ok().body(utilisateur.get());
        }

        return ResponseEntity.notFound().build();
    }

    @JsonView(CustomJsonView.VueUtilisateur.class)
    @GetMapping("/utilisateur/{id}")
    public ResponseEntity<Utilisateur> getUtilisateur(@PathVariable int id) {

        Optional<Utilisateur> utilisateur = utilisateurDao.findById(id);

        if (utilisateur.isPresent()) {
            return ResponseEntity.ok(utilisateur.get());
        } else {
            return ResponseEntity.noContent().build();
        }

    }

    @JsonView(CustomJsonView.VueUtilisateur.class)
    @GetMapping("/listeUtilisateurs")
    public ResponseEntity<List<Utilisateur>> getListUtilisateurs() {

        return ResponseEntity.ok(utilisateurDao.findAll());
    }


    @DeleteMapping("/utilisateur/{id}")
    public ResponseEntity<Integer> deleteUtilisateur(@PathVariable int id) {

        if (utilisateurDao.existsById(id)) {
            utilisateurDao.deleteById(id);
            return ResponseEntity.ok().body(id);
        }
        else {
            return ResponseEntity.noContent().build();
        }

    }

    @PostMapping("/utilisateur")
    public ResponseEntity<String> addUtilisateur(@RequestBody Utilisateur utilisateur) {

        utilisateur = utilisateurDao.saveAndFlush(utilisateur);

        return ResponseEntity.created(URI.create("/flux/" + utilisateur.getId())).build();

    }

}
