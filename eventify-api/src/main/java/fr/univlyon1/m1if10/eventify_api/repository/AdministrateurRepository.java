package fr.univlyon1.m1if10.eventify_api.repository;

import fr.univlyon1.m1if10.eventify_api.entity.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministrateurRepository extends JpaRepository<Administrateur, Integer> {

    Optional<Administrateur> findByUtilisateurId(Integer UtilisateurId);

    boolean existsByUtilisateurId(Integer id);
}