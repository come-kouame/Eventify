package fr.univlyon1.m1if10.eventify_api.service;

import fr.univlyon1.m1if10.eventify_api.dto.EvenementDTO;
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

    public List<EvenementDTO> getAllEvenements() {
        return repository.findAll().stream()
                .map(e -> {
                    EvenementDTO dto = new EvenementDTO();
                    dto.setId(e.getId());
                    dto.setTitre(e.getTitre());
                    dto.setDescription(e.getDescription());
                    dto.setDate(e.getDateEvent().toString());
                    dto.setPrix(e.getPrix().doubleValue());
                    return dto;
                })
                .toList();
    }
}