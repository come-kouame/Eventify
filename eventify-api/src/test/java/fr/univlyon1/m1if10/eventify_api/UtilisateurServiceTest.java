package fr.univlyon1.m1if10.eventify_api;

import fr.univlyon1.m1if10.eventify_api.dto.AuthResponseDTO;
import fr.univlyon1.m1if10.eventify_api.dto.LoginDTO;
import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurCreateDTO;
import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurDTO;
import fr.univlyon1.m1if10.eventify_api.entity.Administrateur;
import fr.univlyon1.m1if10.eventify_api.entity.Organisateur;
import fr.univlyon1.m1if10.eventify_api.entity.Utilisateur;
import fr.univlyon1.m1if10.eventify_api.repository.AdministrateurRepository;
import fr.univlyon1.m1if10.eventify_api.repository.OrganisateurRepository;
import fr.univlyon1.m1if10.eventify_api.repository.UtilisateurRepository;
import fr.univlyon1.m1if10.eventify_api.service.RoleService;
import fr.univlyon1.m1if10.eventify_api.service.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UtilisateurServiceTest {

    private UtilisateurService createService(UtilisateurRepository repo, OrganisateurRepository orgaRepo, AdministrateurRepository adminRepo, PasswordEncoder enc, RoleService roleSvc) {
        return new UtilisateurService(repo, orgaRepo, adminRepo, enc, roleSvc);
    }

    @Test
    void testGetAllUtilisateurs() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        OrganisateurRepository orgaRepo = Mockito.mock(OrganisateurRepository.class);
        AdministrateurRepository adminRepo = Mockito.mock(AdministrateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = createService(repository, orgaRepo, adminRepo, passwordEncoder, roleService);

        Utilisateur u1 = new Utilisateur();
        u1.setId(1);
        u1.setNom("A");
        
        Utilisateur u2 = new Utilisateur();
        u2.setId(2);
        u2.setNom("B");

        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(u2, u1)); // To test sorting

        Organisateur orga = new Organisateur();
        orga.setId(10);
        Mockito.when(orgaRepo.findByUtilisateurId(1)).thenReturn(Optional.of(orga));
        Mockito.when(adminRepo.findByUtilisateurId(1)).thenReturn(Optional.empty());
        Mockito.when(orgaRepo.findByUtilisateurId(2)).thenReturn(Optional.empty());
        Mockito.when(adminRepo.findByUtilisateurId(2)).thenReturn(Optional.empty());

        List<UtilisateurDTO> result = service.getAllUtilisateurs();

        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getNom());
        assertEquals(10, result.get(0).getId_orga());
        assertEquals("B", result.get(1).getNom());
    }

    @Test
    void testGetUtilisateurExistant() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        OrganisateurRepository orgaRepo = Mockito.mock(OrganisateurRepository.class);
        AdministrateurRepository adminRepo = Mockito.mock(AdministrateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = createService(repository, orgaRepo, adminRepo, passwordEncoder, roleService);

        Utilisateur user = new Utilisateur();
        user.setId(1);
        user.setNom("Dupont");
        user.setPrenom("Jean");

        Mockito.when(repository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(orgaRepo.findByUtilisateurId(1)).thenReturn(Optional.empty());
        Mockito.when(adminRepo.findByUtilisateurId(1)).thenReturn(Optional.empty());

        UtilisateurDTO result = service.getUtilisateurById(1);

        assertEquals(1, result.getId());
        assertEquals("Dupont", result.getNom());
    }

    @Test
    void testGetUtilisateurInexistant() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        OrganisateurRepository orgaRepo = Mockito.mock(OrganisateurRepository.class);
        AdministrateurRepository adminRepo = Mockito.mock(AdministrateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = createService(repository, orgaRepo, adminRepo, passwordEncoder, roleService);

        Mockito.when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.getUtilisateurById(99));
    }

    @Test
    void testCreateUtilisateurSuccess() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        OrganisateurRepository orgaRepo = Mockito.mock(OrganisateurRepository.class);
        AdministrateurRepository adminRepo = Mockito.mock(AdministrateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = createService(repository, orgaRepo, adminRepo, passwordEncoder, roleService);

        UtilisateurCreateDTO dto = new UtilisateurCreateDTO();
        dto.setEmail("alice@test.com");
        dto.setMdp("password123");

        Utilisateur savedUser = new Utilisateur();
        savedUser.setId(2);
        savedUser.setEmail("alice@test.com");

        Mockito.when(repository.existsByEmail("alice@test.com")).thenReturn(false);
        Mockito.when(passwordEncoder.encode("password123")).thenReturn("encodedPassword123");
        Mockito.when(repository.save(Mockito.any(Utilisateur.class))).thenReturn(savedUser);

        UtilisateurDTO result = service.createUtilisateur(dto);

        assertEquals(2, result.getId());
        assertEquals("alice@test.com", result.getEmail());
    }

    @Test
    void testCreateUtilisateurEmailConflict() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        OrganisateurRepository orgaRepo = Mockito.mock(OrganisateurRepository.class);
        AdministrateurRepository adminRepo = Mockito.mock(AdministrateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = createService(repository, orgaRepo, adminRepo, passwordEncoder, roleService);

        UtilisateurCreateDTO dto = new UtilisateurCreateDTO();
        dto.setEmail("exist@test.com");

        Mockito.when(repository.existsByEmail("exist@test.com")).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> service.createUtilisateur(dto));
    }

    @Test
    void testEditUtilisateurSuccess() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        OrganisateurRepository orgaRepo = Mockito.mock(OrganisateurRepository.class);
        AdministrateurRepository adminRepo = Mockito.mock(AdministrateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = createService(repository, orgaRepo, adminRepo, passwordEncoder, roleService);

        UtilisateurCreateDTO dto = new UtilisateurCreateDTO();
        dto.setNom("Martin Modif");
        dto.setEmail("alice2@test.com");
        dto.setMdp("newpassword123");

        Utilisateur existingUser = new Utilisateur();
        existingUser.setId(1);
        existingUser.setNom("Martin");

        Mockito.when(repository.findById(1)).thenReturn(Optional.of(existingUser));
        Mockito.when(passwordEncoder.encode("newpassword123")).thenReturn("newEncoded");
        Mockito.when(repository.save(Mockito.any(Utilisateur.class))).thenAnswer(i -> i.getArguments()[0]);

        UtilisateurDTO result = service.editUtilisateur(1, dto);

        assertEquals("Martin Modif", result.getNom());
        assertEquals("alice2@test.com", result.getEmail());
    }

    @Test
    void testEditUtilisateurNotFound() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        OrganisateurRepository orgaRepo = Mockito.mock(OrganisateurRepository.class);
        AdministrateurRepository adminRepo = Mockito.mock(AdministrateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = createService(repository, orgaRepo, adminRepo, passwordEncoder, roleService);

        Mockito.when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.editUtilisateur(99, new UtilisateurCreateDTO()));
    }

    @Test
    void testDeleteUtilisateur() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        OrganisateurRepository orgaRepo = Mockito.mock(OrganisateurRepository.class);
        AdministrateurRepository adminRepo = Mockito.mock(AdministrateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = createService(repository, orgaRepo, adminRepo, passwordEncoder, roleService);

        Utilisateur user = new Utilisateur();
        user.setId(1);

        Mockito.when(repository.findById(1)).thenReturn(Optional.of(user));

        service.deleteUtilisateur(1);
        Mockito.verify(repository).deleteById(1);
    }

    @Test
    void testLoginSuccess() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        OrganisateurRepository orgaRepo = Mockito.mock(OrganisateurRepository.class);
        AdministrateurRepository adminRepo = Mockito.mock(AdministrateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = createService(repository, orgaRepo, adminRepo, passwordEncoder, roleService);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        LoginDTO dto = new LoginDTO();
        dto.setEmail("jdupont@test.com");
        dto.setMdp("password123");

        Utilisateur user = new Utilisateur();
        user.setId(1);
        user.setEmail("jdupont@test.com");
        user.setMdp("$2a$encodedPassword");

        Mockito.when(repository.findByEmail("jdupont@test.com")).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches("password123", "$2a$encodedPassword")).thenReturn(true);
        Mockito.when(request.getSession(true)).thenReturn(session);
        Mockito.when(roleService.getRole(1)).thenReturn("USER");

        AuthResponseDTO response = service.login(dto, request);

        assertEquals("jdupont@test.com", response.getEmail());
    }

    @Test
    void testLoginSuccessUnHashed() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        OrganisateurRepository orgaRepo = Mockito.mock(OrganisateurRepository.class);
        AdministrateurRepository adminRepo = Mockito.mock(AdministrateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = createService(repository, orgaRepo, adminRepo, passwordEncoder, roleService);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        LoginDTO dto = new LoginDTO();
        dto.setEmail("jdupont@test.com");
        dto.setMdp("password123");

        Utilisateur user = new Utilisateur();
        user.setId(1);
        user.setEmail("jdupont@test.com");
        user.setMdp("password123"); // Non hashé

        Mockito.when(repository.findByEmail("jdupont@test.com")).thenReturn(Optional.of(user));
        Mockito.when(request.getSession(true)).thenReturn(session);

        AuthResponseDTO response = service.login(dto, request);

        assertEquals("jdupont@test.com", response.getEmail());
    }

    @Test
    void testLogout() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        OrganisateurRepository orgaRepo = Mockito.mock(OrganisateurRepository.class);
        AdministrateurRepository adminRepo = Mockito.mock(AdministrateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = createService(repository, orgaRepo, adminRepo, passwordEncoder, roleService);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(request.getSession(false)).thenReturn(session);
        service.logout(request);
        Mockito.verify(session).invalidate();
    }

    @Test
    void testSetOrganisateur() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        OrganisateurRepository orgaRepo = Mockito.mock(OrganisateurRepository.class);
        AdministrateurRepository adminRepo = Mockito.mock(AdministrateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = createService(repository, orgaRepo, adminRepo, passwordEncoder, roleService);

        Mockito.when(orgaRepo.existsByUtilisateurId(1)).thenReturn(false);

        service.setOrganisateur(1);

        Mockito.verify(adminRepo).deleteById(1);
        Mockito.verify(orgaRepo).save(Mockito.any(Organisateur.class));
    }

    @Test
    void testSetAdministrateurWithExistingOrga() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        OrganisateurRepository orgaRepo = Mockito.mock(OrganisateurRepository.class);
        AdministrateurRepository adminRepo = Mockito.mock(AdministrateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = createService(repository, orgaRepo, adminRepo, passwordEncoder, roleService);

        Mockito.when(adminRepo.existsByUtilisateurId(1)).thenReturn(false);
        Organisateur orga = new Organisateur();
        orga.setId(10);
        Mockito.when(orgaRepo.findByUtilisateurId(1)).thenReturn(Optional.of(orga));

        service.setAdministrateur(1);

        Mockito.verify(adminRepo).save(Mockito.any(Administrateur.class));
    }

    @Test
    void testSetAdministrateurWithoutExistingOrga() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        OrganisateurRepository orgaRepo = Mockito.mock(OrganisateurRepository.class);
        AdministrateurRepository adminRepo = Mockito.mock(AdministrateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = createService(repository, orgaRepo, adminRepo, passwordEncoder, roleService);

        Mockito.when(adminRepo.existsByUtilisateurId(1)).thenReturn(false);
        Mockito.when(orgaRepo.findByUtilisateurId(1)).thenReturn(Optional.empty());

        service.setAdministrateur(1);

        Mockito.verify(orgaRepo).save(Mockito.any(Organisateur.class));
        Mockito.verify(adminRepo).save(Mockito.any(Administrateur.class));
    }

    @Test
    void testSetUtilisateur() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        OrganisateurRepository orgaRepo = Mockito.mock(OrganisateurRepository.class);
        AdministrateurRepository adminRepo = Mockito.mock(AdministrateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = createService(repository, orgaRepo, adminRepo, passwordEncoder, roleService);

        Administrateur admin = new Administrateur();
        Organisateur orga = new Organisateur();
        Mockito.when(adminRepo.findByUtilisateurId(1)).thenReturn(Optional.of(admin));
        Mockito.when(orgaRepo.findByUtilisateurId(1)).thenReturn(Optional.of(orga));

        service.setUtilisateur(1);

        Mockito.verify(adminRepo).delete(admin);
        Mockito.verify(orgaRepo).delete(orga);
    }
}