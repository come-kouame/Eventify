package fr.univlyon1.m1if10.eventify_api.service;
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

    public void participer(Integer utilisateurId, Integer evenementId) {
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

    public List<Participation> getParticipantsByEvent(Integer evenementId) {
        return repository.findByEvenementId(evenementId);
    }
}