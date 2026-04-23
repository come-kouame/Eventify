package fr.univlyon1.m1if10.eventify_api.service;
import fr.univlyon1.m1if10.eventify_api.dto.EvenementDTO;
import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurDTO;
import fr.univlyon1.m1if10.eventify_api.entity.Evenement;
import fr.univlyon1.m1if10.eventify_api.entity.Participation;
import fr.univlyon1.m1if10.eventify_api.entity.Utilisateur;
import fr.univlyon1.m1if10.eventify_api.repository.ParticipationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipationService {

    private final ParticipationRepository repository;

    public ParticipationService(ParticipationRepository repository) {
        this.repository = repository;
    }

    public void inscrireUtilisateur(Integer utilisateurId, Integer evenementId) {
        if (repository.existsByUtilisateurIdAndEvenementId(utilisateurId, evenementId)) {
            throw new RuntimeException("Déjà inscrit");
        }

        Participation p = new Participation();

        Utilisateur u = new Utilisateur();
        u.setId(utilisateurId);

        Evenement e = new Evenement();
        e.setId(evenementId);

        p.setUtilisateur(u);
        p.setEvenement(e);

        repository.save(p);
    }

    public List<UtilisateurDTO> getParticipantsByEvent(int id) {
        return repository.findByEvenementId(id).stream()
                .map(p -> {
                    UtilisateurDTO dto = new UtilisateurDTO();
                    Utilisateur tempu = p.getUtilisateur();
                    dto.setId(tempu.getId());
                    dto.setNom(tempu.getNom());
                    dto.setPrenom(tempu.getPrenom());
                    return dto;
                })
                .toList();
    }
    public List<EvenementDTO> getEvenementsByUtilisateur(int id) {
        return repository.findByEvenementId(id).stream()
                .map(p -> {
                    EvenementDTO dto = toDTO(p.getEvenement());
                    return dto;
                })
                .toList();
    }

    private EvenementDTO toDTO(Evenement e) {
        EvenementDTO dto = new EvenementDTO();

        dto.setId(e.getId());
        dto.setTitre(e.getTitre());
        dto.setDescription(e.getDescription());
        dto.setDate_debut(e.getDate_debut());
        dto.setDate_fin(e.getDate_fin());
        dto.setLieu(e.getLieu());
        dto.setAdresse(e.getAdresse());
        dto.setPoster(e.getPoster());
        dto.setPrix(e.getPrix());
        dto.setNb_places(e.getNb_places());
        dto.setNb_places_vendues(e.getNb_places_vendues());
        dto.setValide(e.isValide());
        dto.setOrga_id(e.getOrgaId());
        dto.setAdmin_id(e.getAdminId());

        return dto;
    }
}