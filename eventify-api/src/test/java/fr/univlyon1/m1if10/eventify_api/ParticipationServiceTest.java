package fr.univlyon1.m1if10.eventify_api;

import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurDTO;
import fr.univlyon1.m1if10.eventify_api.entity.Evenement;
import fr.univlyon1.m1if10.eventify_api.entity.Participation;
import fr.univlyon1.m1if10.eventify_api.entity.Utilisateur;
import fr.univlyon1.m1if10.eventify_api.repository.EvenementRepository;
import fr.univlyon1.m1if10.eventify_api.repository.ParticipationRepository;
import fr.univlyon1.m1if10.eventify_api.service.ParticipationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ParticipationServiceTest {

    @Test
    void testInscrireUtilisateurSucces() {
        ParticipationRepository repository = Mockito.mock(ParticipationRepository.class);
        EvenementRepository eventRepo = Mockito.mock(EvenementRepository.class);
        ParticipationService service = new ParticipationService(repository, eventRepo);

        Mockito.when(repository.existsByUtilisateurIdAndEvenementId(1, 2))
                .thenReturn(false);

        Evenement ev = new Evenement();
        ev.setNb_places(10);
        ev.setNb_places_vendues(5);
        Mockito.when(eventRepo.findById(2)).thenReturn(Optional.of(ev));

        service.inscrireUtilisateur(1, 2);

        Mockito.verify(repository).save(Mockito.any(Participation.class));
        Mockito.verify(eventRepo).save(ev);
        assertEquals(6, ev.getNb_places_vendues());
    }

    @Test
    void testInscrireUtilisateurDejaInscrit() {
        ParticipationRepository repository = Mockito.mock(ParticipationRepository.class);
        EvenementRepository eventRepo = Mockito.mock(EvenementRepository.class);
        ParticipationService service = new ParticipationService(repository, eventRepo);

        Mockito.when(repository.existsByUtilisateurIdAndEvenementId(1, 2))
                .thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            service.inscrireUtilisateur(1, 2);
        });
    }

    @Test
    void testInscrireUtilisateurPlusDePlaces() {
        ParticipationRepository repository = Mockito.mock(ParticipationRepository.class);
        EvenementRepository eventRepo = Mockito.mock(EvenementRepository.class);
        ParticipationService service = new ParticipationService(repository, eventRepo);

        Mockito.when(repository.existsByUtilisateurIdAndEvenementId(1, 2))
                .thenReturn(false);

        Evenement ev = new Evenement();
        ev.setNb_places(10);
        ev.setNb_places_vendues(10); // Plus de places
        Mockito.when(eventRepo.findById(2)).thenReturn(Optional.of(ev));

        assertThrows(RuntimeException.class, () -> {
            service.inscrireUtilisateur(1, 2);
        });
    }

    @Test
    void testGetParticipantsByEvent() {
        ParticipationRepository repository = Mockito.mock(ParticipationRepository.class);
        EvenementRepository eventRepo = Mockito.mock(EvenementRepository.class);
        ParticipationService service = new ParticipationService(repository, eventRepo);

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1);
        utilisateur.setNom("Kouame");
        utilisateur.setPrenom("Come");

        Participation participation = new Participation();
        participation.setUtilisateur(utilisateur);

        Mockito.when(repository.findByEvenementId(2))
                .thenReturn(List.of(participation));

        List<UtilisateurDTO> result = service.getParticipantsByEvent(2);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("Kouame", result.get(0).getNom());
        assertEquals("Come", result.get(0).getPrenom());
    }
}