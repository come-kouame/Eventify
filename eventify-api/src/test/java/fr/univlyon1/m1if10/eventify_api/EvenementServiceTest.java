package fr.univlyon1.m1if10.eventify_api;

import fr.univlyon1.m1if10.eventify_api.dto.EvenementDTO;
import fr.univlyon1.m1if10.eventify_api.entity.Evenement;
import fr.univlyon1.m1if10.eventify_api.repository.EvenementRepository;
import fr.univlyon1.m1if10.eventify_api.service.EvenementService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

public class EvenementServiceTest {

    @Test
    void testCreateEvenementSimple() {

        EvenementRepository repository = Mockito.mock(EvenementRepository.class);
        EvenementService service = new EvenementService(repository);

        EvenementDTO dto = new EvenementDTO();
        dto.setTitre("Concert");
        dto.setDescription("Description test");
        dto.setDate_debut(java.time.LocalDate.now());
        dto.setDate_fin(java.time.LocalDate.now().plusDays(1));
        dto.setLieu("Lyon");
        dto.setAdresse("10 rue test");
        dto.setPoster("poster.png");
        dto.setPrix(20.0);
        dto.setNb_places(100);
        dto.setNb_places_vendues(0);
        dto.setOrga_id(1);
        dto.setAdmin_id(1);

        Evenement saved = new Evenement();
        saved.setId(1);
        saved.setTitre("Concert");
        saved.setDescription("Description test");
        saved.setDate_debut(java.time.LocalDate.now());
        saved.setDate_fin(java.time.LocalDate.now().plusDays(1));
        saved.setLieu("Lyon");
        saved.setAdresse("10 rue test");
        saved.setPoster("poster.png");
        saved.setPrix(20.0);
        saved.setNb_places(100);
        saved.setNb_places_vendues(0);
        saved.setValide(false);
        saved.setOrgaId(1);
        saved.setAdminId(1);

        Mockito.when(repository.save(Mockito.any(Evenement.class)))
                .thenReturn(saved);

        EvenementDTO result = service.createEvenement(dto);

        assertEquals("Concert", result.getTitre());
        assertEquals(100, result.getNb_places());
        assertEquals(0, result.getNb_places_vendues());
    }

    @Test
    void testGetEvenementExistant() {

        EvenementRepository repository = Mockito.mock(EvenementRepository.class);
        EvenementService service = new EvenementService(repository);

        Evenement evenement = new Evenement();
        evenement.setId(1);
        evenement.setTitre("Concert");
        evenement.setDescription("Description test");
        evenement.setDate_debut(java.time.LocalDate.now());
        evenement.setDate_fin(java.time.LocalDate.now().plusDays(1));
        evenement.setLieu("Lyon");
        evenement.setAdresse("10 rue test");
        evenement.setPoster("poster.png");
        evenement.setPrix(20.0);
        evenement.setNb_places(100);
        evenement.setNb_places_vendues(0);
        evenement.setValide(false);
        evenement.setOrgaId(1);
        evenement.setAdminId(1);

        Mockito.when(repository.findById(1))
                .thenReturn(java.util.Optional.of(evenement));

        EvenementDTO result = service.getEvenementById(1);

        assertEquals(1, result.getId());
        assertEquals("Concert", result.getTitre());
        assertEquals("Lyon", result.getLieu());
        assertEquals(100, result.getNb_places());
    }

    @Test
    void testGetEvenementInexistant() {

        EvenementRepository repository = Mockito.mock(EvenementRepository.class);
        EvenementService service = new EvenementService(repository);

        Mockito.when(repository.findById(99))
                .thenReturn(java.util.Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            service.getEvenementById(99);
        });
    } 

    @Test
    void testEditEvenement() {

        EvenementRepository repository = Mockito.mock(EvenementRepository.class);
        EvenementService service = new EvenementService(repository);

        Evenement existing = new Evenement();
        existing.setId(1);
        existing.setTitre("Ancien titre");

        EvenementDTO dto = new EvenementDTO();
        dto.setTitre("Nouveau titre");
        dto.setDescription("Nouvelle description");
        dto.setDate_debut(java.time.LocalDate.now());
        dto.setDate_fin(java.time.LocalDate.now().plusDays(1));
        dto.setLieu("Paris");
        dto.setAdresse("20 rue test");
        dto.setPoster("newposter.png");
        dto.setPrix(30.0);
        dto.setNb_places(200);
        dto.setNb_places_vendues(10);
        dto.setValide(false);
        dto.setOrga_id(2);
        dto.setAdmin_id(1);

        Mockito.when(repository.findById(1))
                .thenReturn(java.util.Optional.of(existing));

        Mockito.when(repository.save(Mockito.any(Evenement.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        EvenementDTO result = service.editEvenement(1, dto);

        assertEquals("Nouveau titre", result.getTitre());
        assertEquals("Paris", result.getLieu());
        assertEquals(30.0, result.getPrix());
        assertEquals(200, result.getNb_places());
    }

    @Test
    void testDeleteEvenementExistant() {

        EvenementRepository repository = Mockito.mock(EvenementRepository.class);
        EvenementService service = new EvenementService(repository);

        Evenement evenement = new Evenement();
        evenement.setId(1);

        Mockito.when(repository.findById(1))
                .thenReturn(java.util.Optional.of(evenement));

        service.deleteEvenement(1);

        Mockito.verify(repository).deleteById(1);
    }

    @Test
    void testDeleteEvenementInexistant() {

        EvenementRepository repository = Mockito.mock(EvenementRepository.class);
        EvenementService service = new EvenementService(repository);

        Mockito.when(repository.findById(99))
                .thenReturn(java.util.Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            service.deleteEvenement(99);
        });

        Mockito.verify(repository, Mockito.never()).deleteById(99);
    }
}