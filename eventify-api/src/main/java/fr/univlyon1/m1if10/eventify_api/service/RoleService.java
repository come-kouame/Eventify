package fr.univlyon1.m1if10.eventify_api.service;

import fr.univlyon1.m1if10.eventify_api.repository.AdministrateurRepository;
import fr.univlyon1.m1if10.eventify_api.repository.OrganisateurRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final AdministrateurRepository adminRepo;
    private final OrganisateurRepository orgaRepo;

    public RoleService(OrganisateurRepository orgaRepo, AdministrateurRepository adminRepo) {
        this.orgaRepo = orgaRepo;
        this.adminRepo = adminRepo;
    }

    public boolean isOrganisateur(Integer userId) {
        return orgaRepo.existsByUtilisateurId(userId);
    }

    public boolean isAdministrateur(Integer userId) {
        return adminRepo.findAll().stream()
                .anyMatch(a -> a.getUtilisateurId().equals(userId));
    }

    public String getRole(Integer userId) {
        if (isAdministrateur(userId)) return "ADMIN";
        if (isOrganisateur(userId)) return "ORGANISATEUR";
        return "USER";
    }
}
