package fr.univlyon1.m1if10.eventify_api.repository;

import fr.univlyon1.m1if10.eventify_api.entity.Organisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganisateurRepository extends JpaRepository<Organisateur, Integer> {
    boolean existsByUtilisateurId(Integer utilisateurId);
    Optional<Organisateur> findByUtilisateurId(Integer utilisateurId);
}