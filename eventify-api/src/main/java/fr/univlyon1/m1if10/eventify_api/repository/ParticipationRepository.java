package fr.univlyon1.m1if10.eventify_api.repository;

import fr.univlyon1.m1if10.eventify_api.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Integer> {

    List<Participation> findByEvenementId(Integer evenementId);

    List<Participation> findByUtilisateurId(Integer utilisateurId);

    boolean existsByUtilisateurIdAndEvenementId(Integer utilisateurId, Integer evenementId);
}