package fr.univlyon1.m1if10.eventify_api.controller;

import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurCreateDTO;
import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurDTO;
import fr.univlyon1.m1if10.eventify_api.entity.Utilisateur;
import fr.univlyon1.m1if10.eventify_api.service.UtilisateurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utilisateurs")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
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

    // Récupérer un utilisateur par son ID
    @GetMapping("/{id}")
    public UtilisateurDTO getUtilisateurById(@PathVariable Integer id) {
        return service.getUtilisateurById(id);
    }

    // Créer un nouvel utilisateur
    @PostMapping
    public UtilisateurDTO createUtilisateur(@RequestBody UtilisateurCreateDTO dto) {
        return service.createUtilisateur(dto);
    }

    // Modifier un utilisateur existant
    @PutMapping
    public UtilisateurDTO editUtilisateur(@PathVariable Integer id, @RequestBody UtilisateurCreateDTO dto) {
        return service.editUtilisateur(id, dto);
    }

    // Supprimer un utilisateur par son ID
    @DeleteMapping("/{id}")
    public void deleteUtilisateur(@PathVariable Integer id) {
        service.deleteUtilisateur(id);
    }
}