package fr.univlyon1.m1if10.eventify_api.service;

import fr.univlyon1.m1if10.eventify_api.dto.AuthResponseDTO;
import fr.univlyon1.m1if10.eventify_api.dto.LoginDTO;
import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurCreateDTO;
import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurDTO;
import fr.univlyon1.m1if10.eventify_api.entity.Utilisateur;
import fr.univlyon1.m1if10.eventify_api.repository.UtilisateurRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UtilisateurService {

    private final UtilisateurRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UtilisateurService(UtilisateurRepository repository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
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
        if (repository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email déjà utilisé");
        }
        if (repository.existsByLogin(dto.getLogin())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Login déjà utilisé");
        }
        Utilisateur u = new Utilisateur();
        u.setNom(dto.getNom());
        u.setPrenom(dto.getPrenom());
        u.setDateNaissance(dto.getDateNaissance());
        u.setEmail(dto.getEmail());
        u.setVille(dto.getVille());
        u.setRue(dto.getRue());
        u.setCodePostal(dto.getCodePostal());
        u.setLogin(dto.getLogin());
        u.setMdp(passwordEncoder.encode(dto.getMdp()));
        Utilisateur saved = repository.save(u);
        return toDTO(saved);
    }

    public void deleteUtilisateur(Integer id) {
        repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé " + id));
        repository.deleteById(id);
    }

    public AuthResponseDTO login(LoginDTO dto, HttpServletRequest request) {
    System.out.println("LOGIN RECU EMAIL = " + dto.getEmail());
    System.out.println("LOGIN RECU MDP = " + dto.getMdp());

    Utilisateur user = repository.findByEmail(dto.getEmail())
            .orElseThrow(() -> {
                System.out.println("AUCUN USER AVEC CET EMAIL");
                return new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Identifiants invalides"
                );
            });

    System.out.println("USER TROUVE = " + user.getEmail());
    System.out.println("MDP BDD = " + user.getMdp());

    if (!dto.getMdp().equals(user.getMdp())) {
        System.out.println("MDP DIFFERENT");
        throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Identifiants invalides"
        );
    }

    System.out.println("MDP OK");

    HttpSession session = request.getSession(true);
    session.setAttribute("userId", user.getId());
    session.setAttribute("login", user.getLogin());
    session.setAttribute("role", roleService.getRole(user.getId()));

    AuthResponseDTO res = new AuthResponseDTO();
    res.setLogin(user.getLogin());
    return res;
}
    public Utilisateur getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Non connecté");
        }

        Integer userId = (Integer) session.getAttribute("userId");

        return repository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Session invalide"
                ));
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