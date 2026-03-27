package fr.univlyon1.m1if10.eventify_api.service;

import fr.univlyon1.m1if10.eventify_api.entity.Evenement;
import fr.univlyon1.m1if10.eventify_api.repository.EvenementRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EvenementService {

    private final EvenementRepository repository;

    public EvenementService(EvenementRepository repository) {
        this.repository = repository;
    }

    public List<Evenement> getAllEvenements() {
        return repository.findAll();
    }
}