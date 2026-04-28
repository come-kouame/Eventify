package fr.univlyon1.m1if10.eventify_api;

import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurDTO;
import fr.univlyon1.m1if10.eventify_api.entity.Evenement;
import fr.univlyon1.m1if10.eventify_api.entity.Participation;
import fr.univlyon1.m1if10.eventify_api.entity.Utilisateur;
import fr.univlyon1.m1if10.eventify_api.repository.EvenementRepository;
import fr.univlyon1.m1if10.eventify_api.repository.ParticipationRepository;
import fr.univlyon1.m1if10.eventify_api.repository.UtilisateurRepository;
import fr.univlyon1.m1if10.eventify_api.service.ParticipationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ParticipationServiceTest {

    private ParticipationRepository repository;
    private EvenementRepository evenementRepository;
    private UtilisateurRepository utilisateurRepository;
    private ParticipationService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ParticipationRepository.class);
        evenementRepository = Mockito.mock(EvenementRepository.class);
        utilisateurRepository = Mockito.mock(UtilisateurRepository.class);
        service = new ParticipationService(repository, utilisateurRepository, evenementRepository);
    }

    @Test
    void testInscrireUtilisateurSucces() {
        Evenement evenement = new Evenement();
        evenement.setId(2);
        evenement.setNb_places(10);
        evenement.setNb_places_vendues(0);

        Mockito.when(repository.existsByUtilisateurIdAndEvenementId(2, 1)).thenReturn(false);
Mockito.when(evenementRepository.findById(2))
                .thenReturn(Optional.of(evenement));
        Mockito.when(utilisateurRepository.getReferenceById(1))
                .thenReturn(new Utilisateur());

        service.inscrireUtilisateur(1, 2);

        assertEquals(1, evenement.getNb_places_vendues());
        Mockito.verify(evenementRepository).save(evenement);
        Mockito.verify(repository).save(Mockito.any(Participation.class));
    }

    @Test
    void testInscrireUtilisateurDejaInscrit() {
        Mockito.when(repository.existsByUtilisateurIdAndEvenementId(2, 1))
                .thenReturn(true);

        assertThrows(RuntimeException.class, () -> service.inscrireUtilisateur(1, 2));

        Mockito.verify(evenementRepository, Mockito.never()).findById(Mockito.anyInt());
        Mockito.verify(repository, Mockito.never()).save(Mockito.any(Participation.class));
    }

    @Test
    void testInscrireUtilisateurEvenementNonTrouve() {
        Mockito.when(repository.existsByUtilisateurIdAndEvenementId(2, 1))
                .thenReturn(false);
        Mockito.when(evenementRepository.findById(2))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.inscrireUtilisateur(1, 2));

        Mockito.verify(repository, Mockito.never()).save(Mockito.any(Participation.class));
    }

    @Test
    void testInscrireUtilisateurPlusDePlaces() {
        Evenement evenement = new Evenement();
        evenement.setId(2);
        evenement.setNb_places(10);
        evenement.setNb_places_vendues(10);

        Mockito.when(repository.existsByUtilisateurIdAndEvenementId(2, 1))
                .thenReturn(false);
        Mockito.when(evenementRepository.findById(2))
                .thenReturn(Optional.of(evenement));

        assertThrows(RuntimeException.class, () -> service.inscrireUtilisateur(1, 2));

        Mockito.verify(repository, Mockito.never()).save(Mockito.any(Participation.class));
    }

    @Test
    void testGetParticipantsByEvent() {
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