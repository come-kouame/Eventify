package fr.univlyon1.m1if10.eventify_api;

import fr.univlyon1.m1if10.eventify_api.entity.Administrateur;
import fr.univlyon1.m1if10.eventify_api.repository.AdministrateurRepository;
import fr.univlyon1.m1if10.eventify_api.repository.OrganisateurRepository;
import fr.univlyon1.m1if10.eventify_api.service.RoleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoleServiceTest {

    @Test
    void testIsOrganisateur() {
        OrganisateurRepository orgaRepo = Mockito.mock(OrganisateurRepository.class);
        AdministrateurRepository adminRepo = Mockito.mock(AdministrateurRepository.class);
        RoleService service = new RoleService(orgaRepo, adminRepo);

        Mockito.when(orgaRepo.existsByUtilisateurId(1)).thenReturn(true);
        Mockito.when(orgaRepo.existsByUtilisateurId(2)).thenReturn(false);

        assertTrue(service.isOrganisateur(1));
        assertFalse(service.isOrganisateur(2));
    }

    @Test
    void testIsAdministrateur() {
        OrganisateurRepository orgaRepo = Mockito.mock(OrganisateurRepository.class);
        AdministrateurRepository adminRepo = Mockito.mock(AdministrateurRepository.class);
        RoleService service = new RoleService(orgaRepo, adminRepo);

        Administrateur admin = new Administrateur();
        admin.setUtilisateurId(1);

        Mockito.when(adminRepo.findAll()).thenReturn(List.of(admin));

        assertTrue(service.isAdministrateur(1));
        assertFalse(service.isAdministrateur(2));
    }

    @Test
    void testGetRole() {
        OrganisateurRepository orgaRepo = Mockito.mock(OrganisateurRepository.class);
        AdministrateurRepository adminRepo = Mockito.mock(AdministrateurRepository.class);
        RoleService service = new RoleService(orgaRepo, adminRepo);

        Administrateur admin = new Administrateur();
        admin.setUtilisateurId(1);
        Mockito.when(adminRepo.findAll()).thenReturn(List.of(admin));

        Mockito.when(orgaRepo.existsByUtilisateurId(2)).thenReturn(true);
        Mockito.when(orgaRepo.existsByUtilisateurId(3)).thenReturn(false);

        assertEquals("ADMIN", service.getRole(1));
        assertEquals("ORGANISATEUR", service.getRole(2));
        assertEquals("USER", service.getRole(3));
    }
}
