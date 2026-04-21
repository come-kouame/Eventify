package fr.univlyon1.m1if10.eventify_api.repository;

import fr.univlyon1.m1if10.eventify_api.entity.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvenementRepository extends JpaRepository<Evenement, Long> {
}