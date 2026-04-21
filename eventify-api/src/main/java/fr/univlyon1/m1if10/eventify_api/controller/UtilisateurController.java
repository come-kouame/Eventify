package fr.univlyon1.m1if10.eventify_api.controller;

import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurDTO;
import fr.univlyon1.m1if10.eventify_api.entity.Utilisateur;
import fr.univlyon1.m1if10.eventify_api.service.UtilisateurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utilisateurs")
@CrossOrigin(origins = "*")
public class UtilisateurController {

    private final UtilisateurService service;

    public UtilisateurController(UtilisateurService service) {
        this.service = service;
    }

    // Récupérer tous les utilisateurs
    @GetMapping
    public List<UtilisateurDTO> getUtilisateurs() {
        return service.getAllUtilisateurs();
    }

    /*
    // Récupérer un utilisateur par son ID
    @GetMapping("/{id}")
    public Utilisateur getUtilisateurById(@PathVariable Long id) {
        return service.getUtilisateurById(id);
    }

    // Créer un nouvel utilisateur
    @PostMapping
    public Utilisateur createUtilisateur(@RequestBody Utilisateur utilisateur) {
        return service.createUtilisateur(utilisateur);
    }

    // Supprimer un utilisateur par son ID
    @DeleteMapping("/{id}")
    public void deleteUtilisateur(@PathVariable Long id) {
        service.deleteUtilisateur(id);
    }
    */
}