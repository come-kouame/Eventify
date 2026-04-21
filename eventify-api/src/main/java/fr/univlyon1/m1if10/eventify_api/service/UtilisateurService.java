package fr.univlyon1.m1if10.eventify_api.service;

import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurDTO;
import fr.univlyon1.m1if10.eventify_api.entity.Utilisateur;
import fr.univlyon1.m1if10.eventify_api.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {

    private final UtilisateurRepository repository;

    public UtilisateurService(UtilisateurRepository repository) {
        this.repository = repository;
    }

    public List<UtilisateurDTO> getAllUtilisateurs() {
        return repository.findAll().stream()
                .map(u -> {
                    UtilisateurDTO dto = new UtilisateurDTO();
                    dto.setId(u.getId());
                    dto.setNom(u.getNom());
                    dto.setPrenom(u.getPrenom());
                    dto.setEmail(u.getEmail());
                    dto.setVille(u.getVille());
                    return dto;
                })
                .toList();
    }
}