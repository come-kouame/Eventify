package fr.univlyon1.m1if10.eventify_api.service;

import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurCreateDTO;
import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurDTO;
import fr.univlyon1.m1if10.eventify_api.entity.Utilisateur;
import fr.univlyon1.m1if10.eventify_api.repository.UtilisateurRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UtilisateurService {

    private final UtilisateurRepository repository;

    public UtilisateurService(UtilisateurRepository repository) {
        this.repository = repository;
    }

    public List<UtilisateurDTO> getAllUtilisateurs() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public UtilisateurDTO getUtilisateurById(Integer id) {
        Utilisateur u = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé " + id));

        return toDTO(u);
    }

    public UtilisateurDTO createUtilisateur(UtilisateurCreateDTO dto) {
        Utilisateur u = new Utilisateur();
        u.setNom(dto.getNom());
        u.setPrenom(dto.getPrenom());
        u.setDateNaissance(dto.getDateNaissance());
        u.setEmail(dto.getEmail());
        u.setVille(dto.getVille());
        u.setRue(dto.getRue());
        u.setCodePostal(dto.getCodePostal());
        u.setLogin(dto.getLogin());
        u.setMdp(dto.getMdp());
        Utilisateur saved = repository.save(u);
        return toDTO(saved);
    }

    public void deleteUtilisateur(Integer id) {
        repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé " + id));
        repository.deleteById(id);
    }

    private UtilisateurDTO toDTO(Utilisateur u) {
        UtilisateurDTO dto = new UtilisateurDTO();
        dto.setId(u.getId());
        dto.setNom(u.getNom());
        dto.setPrenom(u.getPrenom());
        dto.setDateNaissance(u.getDateNaissance());
        dto.setEmail(u.getEmail());
        dto.setVille(u.getVille());
        dto.setRue(u.getRue());
        dto.setCodePostal(u.getCodePostal());
        dto.setLogin(u.getLogin());
        return dto;
    }
}