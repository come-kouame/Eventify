package fr.univlyon1.m1if10.eventify_api.service;

import fr.univlyon1.m1if10.eventify_api.dto.AuthResponseDTO;
import fr.univlyon1.m1if10.eventify_api.dto.LoginDTO;
import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurCreateDTO;
import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurDTO;
import fr.univlyon1.m1if10.eventify_api.entity.Administrateur;
import fr.univlyon1.m1if10.eventify_api.entity.Evenement;
import fr.univlyon1.m1if10.eventify_api.entity.Organisateur;
import fr.univlyon1.m1if10.eventify_api.entity.Utilisateur;
import fr.univlyon1.m1if10.eventify_api.repository.AdministrateurRepository;
import fr.univlyon1.m1if10.eventify_api.repository.OrganisateurRepository;
import fr.univlyon1.m1if10.eventify_api.repository.UtilisateurRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    private final UtilisateurRepository repository;
    private final OrganisateurRepository organisateurRepository;
    private final AdministrateurRepository administrateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UtilisateurService(UtilisateurRepository repository, OrganisateurRepository organisateurRepository, AdministrateurRepository administrateurRepository,PasswordEncoder passwordEncoder, RoleService roleService) {
        this.repository = repository;
        this.organisateurRepository = organisateurRepository;
        this.administrateurRepository = administrateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public List<UtilisateurDTO> getAllUtilisateurs() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(Utilisateur::getNom))
                .map(user -> {
                    UtilisateurDTO dto = toDTO(user);

                    organisateurRepository.findByUtilisateurId(user.getId())
                            .ifPresent(orga -> dto.setId_orga(orga.getId()));

                    administrateurRepository.findByUtilisateurId(user.getId())
                            .ifPresent(admin -> dto.setId_admin(admin.getId()));

                    return dto;
                })
                .toList();
    }

    public UtilisateurDTO getUtilisateurById(Integer id) {
        Utilisateur u = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Utilisateur non trouvé " + id
                ));

        UtilisateurDTO dto = toDTO(u);

        organisateurRepository.findByUtilisateurId(id)
                .ifPresent(orga -> dto.setId_orga(orga.getId()));

        administrateurRepository.findByUtilisateurId(id)
                .ifPresent(admin -> dto.setId_admin(admin.getId()));

        return dto;
    }

    public UtilisateurDTO createUtilisateur(UtilisateurCreateDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email déjà utilisé");
        }
        Utilisateur u = new Utilisateur();
        u.setNom(dto.getNom());
        u.setPrenom(dto.getPrenom());
        u.setDateNaissance(dto.getDateNaissance());
        u.setEmail(dto.getEmail());
        u.setVille(dto.getVille());
        u.setRue(dto.getRue());
        u.setCodePostal(dto.getCodePostal());
        u.setMdp(passwordEncoder.encode(dto.getMdp()));
        Utilisateur saved = repository.save(u);
        return toDTO(saved);
    }

    public UtilisateurDTO editUtilisateur(Integer id, UtilisateurCreateDTO dto) {
        Utilisateur u = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
        u.setNom(dto.getNom());
        u.setPrenom(dto.getPrenom());
        u.setDateNaissance(dto.getDateNaissance());
        u.setEmail(dto.getEmail());
        u.setVille(dto.getVille());
        u.setRue(dto.getRue());
        u.setCodePostal(dto.getCodePostal());
        u.setMdp(passwordEncoder.encode(dto.getMdp()));
        Utilisateur updated = repository.save(u);
        return toDTO(updated);
    }

    public void deleteUtilisateur(Integer id) {
        repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé " + id));
        repository.deleteById(id);
    }

    public AuthResponseDTO login(LoginDTO dto, HttpServletRequest request) {

        System.out.println("DTO RECU = " + dto);
        System.out.println("EMAIL DTO = " + dto.getEmail());
        System.out.println("MDP DTO = " + dto.getMdp());
        Utilisateur user = repository.findByEmail(dto.getEmail())
                .orElseThrow(() -> {
                    return new ResponseStatusException(
                            HttpStatus.UNAUTHORIZED,
                            "Identifiants invalides");
                });

        System.out.println("USER TROUVE = " + user.getEmail());
        System.out.println("MDP DB = " + user.getMdp());

        boolean match;
        //pour les mdp hashés de la bd -kamelia-
        if (user.getMdp().startsWith("$2a$") || user.getMdp().startsWith("$2b$") || user.getMdp().startsWith("$2y$")) {
            match = passwordEncoder.matches(dto.getMdp(), user.getMdp());
        } else { //j'ai remis cette ligne car notre bd contient les anciens user avec mdp non hashés -kamelia-
            match = dto.getMdp().equals(user.getMdp());
        }

        System.out.println("PASSWORD MATCH = " + match);

        if (!match) {
            System.out.println("PASSWORD INCORRECT");
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Identifiants invalides");
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("userId", user.getId());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("role", roleService.getRole(user.getId()));
        AuthResponseDTO res = new AuthResponseDTO();
        res.setEmail(user.getEmail());
        return res;
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    public UtilisateurDTO getCurrentUserDTO(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Non connecté");
        }

        Integer userId = (Integer) session.getAttribute("userId");

        Utilisateur user = repository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Session invalide"));

        UtilisateurDTO dto = toDTO(user);

        organisateurRepository.findByUtilisateurId(userId)
                .ifPresent(orga -> dto.setId_orga(orga.getId()));

        administrateurRepository.findByUtilisateurId(userId)
                .ifPresent(admin -> dto.setId_admin(admin.getId()));

        return dto;
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
        return dto;
    }

    public void setOrganisateur(Integer id) {

        administrateurRepository.deleteById(id);

        if (organisateurRepository.existsByUtilisateurId(id)) {
            return;
        }

        Organisateur orga = new Organisateur();
        orga.setUtilisateurId(id);

        organisateurRepository.save(orga);
    }

    public void setAdministrateur(Integer id) {

        if (administrateurRepository.existsByUtilisateurId(id)) {
            return;
        }


        Optional<Organisateur> orgaOpt = organisateurRepository.findByUtilisateurId(id);

        if (orgaOpt.isPresent()) {
            Organisateur orga = orgaOpt.get();


            Administrateur admin = new Administrateur();
            admin.setUtilisateurId(id);
            admin.setOrganisateurId(orga.getId());

            administrateurRepository.save(admin);
            return;
        }

        else{
            Organisateur orga = new Organisateur();
            orga.setUtilisateurId(id);
            organisateurRepository.save(orga);

            Administrateur admin = new Administrateur();
            admin.setUtilisateurId(id);
            admin.setOrganisateurId(orga.getId());
            administrateurRepository.save(admin);
        }

    }

    public void setUtilisateur(Integer id) {

        administrateurRepository.findByUtilisateurId(id)
                .ifPresent(admin -> administrateurRepository.delete(admin));

        organisateurRepository.findByUtilisateurId(id)
                .ifPresent(orga -> organisateurRepository.delete(orga));
    }
}