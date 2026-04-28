package fr.univlyon1.m1if10.eventify_api;

import fr.univlyon1.m1if10.eventify_api.dto.AuthResponseDTO;
import fr.univlyon1.m1if10.eventify_api.dto.LoginDTO;
import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurCreateDTO;
import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurDTO;
import fr.univlyon1.m1if10.eventify_api.entity.Utilisateur;
import fr.univlyon1.m1if10.eventify_api.repository.AdministrateurRepository;
import fr.univlyon1.m1if10.eventify_api.repository.OrganisateurRepository;
import fr.univlyon1.m1if10.eventify_api.repository.UtilisateurRepository;
import fr.univlyon1.m1if10.eventify_api.service.RoleService;
import fr.univlyon1.m1if10.eventify_api.service.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UtilisateurServiceTest {

    private UtilisateurRepository repository;
    private OrganisateurRepository organisateurRepository;
    private AdministrateurRepository administrateurRepository;
    private PasswordEncoder passwordEncoder;
    private RoleService roleService;
    private UtilisateurService service;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(UtilisateurRepository.class);
        organisateurRepository = Mockito.mock(OrganisateurRepository.class);
        administrateurRepository = Mockito.mock(AdministrateurRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        roleService = Mockito.mock(RoleService.class);

        service = new UtilisateurService(
                repository,
                organisateurRepository,
                administrateurRepository,
                passwordEncoder,
                roleService
        );
    }

    @Test
    void testGetAllUtilisateurs() {
        Utilisateur u1 = new Utilisateur();
        u1.setId(1);
        u1.setNom("Un");

        Utilisateur u2 = new Utilisateur();
        u2.setId(2);
        u2.setNom("Deux");

        Mockito.when(repository.findAll()).thenReturn(List.of(u1, u2));
        Mockito.when(organisateurRepository.findByUtilisateurId(Mockito.anyInt())).thenReturn(Optional.empty());
        Mockito.when(administrateurRepository.findByUtilisateurId(Mockito.anyInt())).thenReturn(Optional.empty());

        List<UtilisateurDTO> result = service.getAllUtilisateurs();

        assertEquals(2, result.size());
        assertEquals("Deux", result.get(0).getNom());
        assertEquals("Un", result.get(1).getNom());
    }

    @Test
    void testGetUtilisateurExistant() {
        Utilisateur user = new Utilisateur();
        user.setId(1);
        user.setNom("Dupont");
        user.setPrenom("Jean");
        user.setEmail("jean@test.com");

        Mockito.when(repository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(organisateurRepository.findByUtilisateurId(1)).thenReturn(Optional.empty());
        Mockito.when(administrateurRepository.findByUtilisateurId(1)).thenReturn(Optional.empty());

        UtilisateurDTO result = service.getUtilisateurById(1);

        assertEquals(1, result.getId());
        assertEquals("Dupont", result.getNom());
        assertEquals("Jean", result.getPrenom());
        assertEquals("jean@test.com", result.getEmail());
    }

    @Test
    void testGetUtilisateurInexistant() {
        Mockito.when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            service.getUtilisateurById(99);
        });
    }

    @Test
    void testCreateUtilisateurSuccess() {
        UtilisateurCreateDTO dto = new UtilisateurCreateDTO();
        dto.setNom("Martin");
        dto.setPrenom("Alice");
        dto.setEmail("alice@test.com");
        dto.setMdp("password123");
        dto.setDateNaissance(LocalDate.of(1995, 5, 15));
        dto.setVille("Lyon");
        dto.setRue("Rue test");
        dto.setCodePostal("69000");

        Utilisateur savedUser = new Utilisateur();
        savedUser.setId(2);
        savedUser.setNom("Martin");
        savedUser.setPrenom("Alice");
        savedUser.setEmail("alice@test.com");

        Mockito.when(repository.existsByEmail("alice@test.com")).thenReturn(false);
        Mockito.when(passwordEncoder.encode("password123")).thenReturn("encodedPassword123");
        Mockito.when(repository.save(Mockito.any(Utilisateur.class))).thenReturn(savedUser);

        UtilisateurDTO result = service.createUtilisateur(dto);

        assertEquals(2, result.getId());
        assertEquals("Martin", result.getNom());
        assertEquals("alice@test.com", result.getEmail());
        Mockito.verify(repository).save(Mockito.any(Utilisateur.class));
    }

    @Test
    void testCreateUtilisateurEmailConflict() {
        UtilisateurCreateDTO dto = new UtilisateurCreateDTO();
        dto.setEmail("exist@test.com");

        Mockito.when(repository.existsByEmail("exist@test.com")).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            service.createUtilisateur(dto);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Email déjà utilisé"));
    }

    @Test
    void testDeleteUtilisateurExistant() {
        Utilisateur user = new Utilisateur();
        user.setId(1);

        Mockito.when(repository.findById(1)).thenReturn(Optional.of(user));

        service.deleteUtilisateur(1);

        Mockito.verify(repository).deleteById(1);
    }

    @Test
    void testDeleteUtilisateurInexistant() {
        Mockito.when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            service.deleteUtilisateur(99);
        });

        Mockito.verify(repository, Mockito.never()).deleteById(99);
    }

    @Test
    void testLoginSuccessAvecMdpNonHashe() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        LoginDTO dto = new LoginDTO();
        dto.setEmail("jean@test.com");
        dto.setMdp("password123");

        Utilisateur user = new Utilisateur();
        user.setId(1);
        user.setEmail("jean@test.com");
        user.setMdp("password123");

        Mockito.when(repository.findByEmail("jean@test.com")).thenReturn(Optional.of(user));
        Mockito.when(request.getSession(true)).thenReturn(session);
        Mockito.when(roleService.getRole(1)).thenReturn("USER");

        AuthResponseDTO response = service.login(dto, request);

        assertEquals("jean@test.com", response.getEmail());
        Mockito.verify(session).setAttribute("userId", 1);
        Mockito.verify(session).setAttribute("email", "jean@test.com");
        Mockito.verify(session).setAttribute("role", "USER");
    }

    @Test
    void testLoginSuccessAvecMdpHashe() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        LoginDTO dto = new LoginDTO();
        dto.setEmail("alice@test.com");
        dto.setMdp("password123");

        Utilisateur user = new Utilisateur();
        user.setId(2);
        user.setEmail("alice@test.com");
        user.setMdp("$2a$10$encodedPassword");

        Mockito.when(repository.findByEmail("alice@test.com")).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches("password123", "$2a$10$encodedPassword")).thenReturn(true);
        Mockito.when(request.getSession(true)).thenReturn(session);
        Mockito.when(roleService.getRole(2)).thenReturn("USER");

        AuthResponseDTO response = service.login(dto, request);

        assertEquals("alice@test.com", response.getEmail());
        Mockito.verify(session).setAttribute("userId", 2);
        Mockito.verify(session).setAttribute("email", "alice@test.com");
        Mockito.verify(session).setAttribute("role", "USER");
    }

    @Test
    void testLoginInvalidPassword() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        LoginDTO dto = new LoginDTO();
        dto.setEmail("jean@test.com");
        dto.setMdp("wrongpassword");

        Utilisateur user = new Utilisateur();
        user.setId(1);
        user.setEmail("jean@test.com");
        user.setMdp("password123");

        Mockito.when(repository.findByEmail("jean@test.com")).thenReturn(Optional.of(user));

        assertThrows(ResponseStatusException.class, () -> {
            service.login(dto, request);
        });
    }

    @Test
    void testLoginInvalidEmail() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        LoginDTO dto = new LoginDTO();
        dto.setEmail("unknown@test.com");
        dto.setEmail("unknown@test.com");
        dto.setMdp("password123");

        Mockito.when(repository.findByEmail("unknown@test.com")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            service.login(dto, request);
        });
    }

    @Test
    void testGetCurrentUserSuccess() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(request.getSession(false)).thenReturn(session);
        Mockito.when(session.getAttribute("userId")).thenReturn(1);

        Utilisateur user = new Utilisateur();
        user.setId(1);
        user.setNom("Connecté");
        user.setEmail("connecte@test.com");

        Mockito.when(repository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(organisateurRepository.findByUtilisateurId(1)).thenReturn(Optional.empty());
        Mockito.when(administrateurRepository.findByUtilisateurId(1)).thenReturn(Optional.empty());

        UtilisateurDTO result = service.getCurrentUserDTO(request);
        assertEquals(1, result.getId());
        assertEquals("Connecté", result.getNom());
        assertEquals("connecte@test.com", result.getEmail());
    }

    @Test
    void testGetCurrentUserNotConnected() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        Mockito.when(request.getSession(false)).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> {
            service.getCurrentUserDTO(request);
            service.getCurrentUserDTO(request);
        });
    }
}