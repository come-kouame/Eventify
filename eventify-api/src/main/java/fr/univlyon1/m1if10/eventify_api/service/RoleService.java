package fr.univlyon1.m1if10.eventify_api.service;

import fr.univlyon1.m1if10.eventify_api.repository.AdministrateurRepository;
import fr.univlyon1.m1if10.eventify_api.repository.OrganisateurRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final AdministrateurRepository adminRepo;
    private final OrganisateurRepository orgaRepo;

    public RoleService(AdministrateurRepository adminRepo,
                       OrganisateurRepository orgaRepo) {
        this.adminRepo = adminRepo;
        this.orgaRepo = orgaRepo;
    }

    public String getRole(Integer userId) {

        if (adminRepo.existsById(userId)) {
            return "ADMIN";
        }

        if (orgaRepo.existsById(userId)) {
            return "ORGANISATEUR";
        }

        return "USER";
    }
}
