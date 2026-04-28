package fr.univlyon1.m1if10.eventify_api.controller;

import fr.univlyon1.m1if10.eventify_api.dto.ChangePasswordDTO;
import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurCreateDTO;
import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurDTO;
import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurUpdateDTO;
import fr.univlyon1.m1if10.eventify_api.entity.Utilisateur;
import fr.univlyon1.m1if10.eventify_api.service.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
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
    @PutMapping("/{id}")
    public UtilisateurDTO editUtilisateur(@PathVariable Integer id, @RequestBody UtilisateurUpdateDTO dto) {
        return service.editUtilisateur(id, dto);
    }

    // Modifier le mot de passe d'un utilisateur
    @PutMapping("/{id}/password")
    public void changePassword(@PathVariable Integer id, @RequestBody ChangePasswordDTO dto, HttpServletRequest request) {
        service.changePassword(id, dto);
    }

    @PutMapping("/{id}/set-to-organisateur")
    public void setOrganisateur(@PathVariable Integer id) {
        service.setOrganisateur(id);
    }

    @PutMapping("/{id}/set-to-administrateur")
    public void setAdministrateur(@PathVariable Integer id) {
        service.setAdministrateur(id);
    }

    @PutMapping("/{id}/set-to-utilisateur")
    public void setUtilisateur(@PathVariable Integer id) {
        service.setUtilisateur(id);
    }

    // Supprimer un utilisateur par son ID
    @DeleteMapping("/{id}")
    public void deleteUtilisateur(@PathVariable Integer id) {
        service.deleteUtilisateur(id);
    }
}