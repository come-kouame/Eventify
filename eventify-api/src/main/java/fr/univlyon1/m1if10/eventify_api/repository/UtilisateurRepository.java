package fr.univlyon1.m1if10.eventify_api.repository;

import fr.univlyon1.m1if10.eventify_api.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
}