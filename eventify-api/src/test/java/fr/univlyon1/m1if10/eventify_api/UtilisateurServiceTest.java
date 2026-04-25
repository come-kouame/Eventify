package fr.univlyon1.m1if10.eventify_api;

import fr.univlyon1.m1if10.eventify_api.dto.AuthResponseDTO;
import fr.univlyon1.m1if10.eventify_api.dto.LoginDTO;
import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurCreateDTO;
import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurDTO;
import fr.univlyon1.m1if10.eventify_api.entity.Utilisateur;
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

    @Test
    void testGetAllUtilisateurs() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = new UtilisateurService(repository, passwordEncoder, roleService);

        Utilisateur u1 = new Utilisateur();
        u1.setId(1);
        u1.setNom("Un");
        
        Utilisateur u2 = new Utilisateur();
        u2.setId(2);
        u2.setNom("Deux");

        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(u1, u2));

        List<UtilisateurDTO> result = service.getAllUtilisateurs();

        assertEquals(2, result.size());
        assertEquals("Un", result.get(0).getNom());
        assertEquals("Deux", result.get(1).getNom());
    }

    @Test
    void testGetUtilisateurExistant() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = new UtilisateurService(repository, passwordEncoder, roleService);

        Utilisateur user = new Utilisateur();
        user.setId(1);
        user.setNom("Dupont");
        user.setPrenom("Jean");
        user.setLogin("jdupont");

        Mockito.when(repository.findById(1)).thenReturn(Optional.of(user));

        UtilisateurDTO result = service.getUtilisateurById(1);

        assertEquals(1, result.getId());
        assertEquals("Dupont", result.getNom());
        assertEquals("Jean", result.getPrenom());
    }

    @Test
    void testGetUtilisateurInexistant() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = new UtilisateurService(repository, passwordEncoder, roleService);

        Mockito.when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            service.getUtilisateurById(99);
        });
    }

    @Test
    void testCreateUtilisateurSuccess() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = new UtilisateurService(repository, passwordEncoder, roleService);

        UtilisateurCreateDTO dto = new UtilisateurCreateDTO();
        dto.setNom("Martin");
        dto.setPrenom("Alice");
        dto.setLogin("amartin");
        dto.setEmail("alice@test.com");
        dto.setMdp("password123");
        dto.setDateNaissance(LocalDate.of(1995, 5, 15));

        Utilisateur savedUser = new Utilisateur();
        savedUser.setId(2);
        savedUser.setNom("Martin");
        savedUser.setPrenom("Alice");
        savedUser.setLogin("amartin");
        savedUser.setEmail("alice@test.com");

        Mockito.when(repository.existsByEmail("alice@test.com")).thenReturn(false);
        Mockito.when(repository.existsByLogin("amartin")).thenReturn(false);
        Mockito.when(passwordEncoder.encode("password123")).thenReturn("encodedPassword123");
        Mockito.when(repository.save(Mockito.any(Utilisateur.class))).thenReturn(savedUser);

        UtilisateurDTO result = service.createUtilisateur(dto);

        assertEquals(2, result.getId());
        assertEquals("Martin", result.getNom());
        assertEquals("amartin", result.getLogin());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Utilisateur.class));
    }

    @Test
    void testCreateUtilisateurEmailConflict() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = new UtilisateurService(repository, passwordEncoder, roleService);

        UtilisateurCreateDTO dto = new UtilisateurCreateDTO();
        dto.setEmail("exist@test.com");

        // On simule que l'email existe déjà dans la base
        Mockito.when(repository.existsByEmail("exist@test.com")).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            service.createUtilisateur(dto);
        });
        
        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Email déjà utilisé"));
    }

    @Test
    void testCreateUtilisateurLoginConflict() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = new UtilisateurService(repository, passwordEncoder, roleService);

        UtilisateurCreateDTO dto = new UtilisateurCreateDTO();
        dto.setEmail("new@test.com");
        dto.setLogin("existlogin");

        Mockito.when(repository.existsByEmail("new@test.com")).thenReturn(false);
        Mockito.when(repository.existsByLogin("existlogin")).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            service.createUtilisateur(dto);
        });
        
        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Login déjà utilisé"));
    }

    @Test
    void testDeleteUtilisateurExistant() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = new UtilisateurService(repository, passwordEncoder, roleService);

        Utilisateur user = new Utilisateur();
        user.setId(1);

        Mockito.when(repository.findById(1)).thenReturn(Optional.of(user));

        service.deleteUtilisateur(1);

        // On vérifie que la méthode deleteById a bien été appelée une fois avec l'id 1
        Mockito.verify(repository, Mockito.times(1)).deleteById(1);
    }

    @Test
    void testDeleteUtilisateurInexistant() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = new UtilisateurService(repository, passwordEncoder, roleService);

        Mockito.when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            service.deleteUtilisateur(99);
        });

        // On vérifie que deleteById n'a jamais été appelée
        Mockito.verify(repository, Mockito.never()).deleteById(99);
    }

    @Test
    void testLoginSuccess() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = new UtilisateurService(repository, passwordEncoder, roleService);

        // Mock de la requête HTTP et de la session (pour le login)
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        LoginDTO dto = new LoginDTO();
        dto.setLogin("jdupont");
        dto.setMdp("password123");

        Utilisateur user = new Utilisateur();
        user.setId(1);
        user.setLogin("jdupont");
        user.setMdp("encodedPassword");

        Mockito.when(repository.findByLogin("jdupont")).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        Mockito.when(request.getSession(true)).thenReturn(session);
        Mockito.when(roleService.getRole(1)).thenReturn("USER");

        AuthResponseDTO response = service.login(dto, request);

        assertEquals("jdupont", response.getLogin());
        Mockito.verify(session).setAttribute("userId", 1);
        Mockito.verify(session).setAttribute("login", "jdupont");
        Mockito.verify(session).setAttribute("role", "USER");
    }

    @Test
    void testLoginInvalidPassword() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = new UtilisateurService(repository, passwordEncoder, roleService);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        LoginDTO dto = new LoginDTO();
        dto.setLogin("jdupont");
        dto.setMdp("wrongpassword");

        Utilisateur user = new Utilisateur();
        user.setId(1);
        user.setLogin("jdupont");
        user.setMdp("encodedPassword");

        Mockito.when(repository.findByLogin("jdupont")).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches("wrongpassword", "encodedPassword")).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> {
            service.login(dto, request);
        });
    }

    @Test
    void testLoginInvalidUsername() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = new UtilisateurService(repository, passwordEncoder, roleService);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        LoginDTO dto = new LoginDTO();
        dto.setLogin("unknown");
        dto.setMdp("password123");

        Mockito.when(repository.findByLogin("unknown")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            service.login(dto, request);
        });
    }

    @Test
    void testGetCurrentUserSuccess() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = new UtilisateurService(repository, passwordEncoder, roleService);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(request.getSession(false)).thenReturn(session);
        Mockito.when(session.getAttribute("userId")).thenReturn(1);

        Utilisateur user = new Utilisateur();
        user.setId(1);
        user.setNom("Connecté");

        Mockito.when(repository.findById(1)).thenReturn(Optional.of(user));

        Utilisateur result = service.getCurrentUser(request);

        assertEquals(1, result.getId());
        assertEquals("Connecté", result.getNom());
    }

    @Test
    void testGetCurrentUserNotConnected() {
        UtilisateurRepository repository = Mockito.mock(UtilisateurRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        RoleService roleService = Mockito.mock(RoleService.class);
        UtilisateurService service = new UtilisateurService(repository, passwordEncoder, roleService);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        // Simule qu'il n'y a pas de session active
        Mockito.when(request.getSession(false)).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> {
            service.getCurrentUser(request);
        });
    }
}
