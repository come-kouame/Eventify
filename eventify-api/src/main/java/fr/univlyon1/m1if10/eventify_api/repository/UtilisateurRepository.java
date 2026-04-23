package fr.univlyon1.m1if10.eventify_api.repository;

import fr.univlyon1.m1if10.eventify_api.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByLogin(String login);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);
}