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
                .map(this::toDTO)
                .toList();
    }

    public EvenementDTO getEvenementById(Integer id) {
        Evenement e = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evenement not found"));

        return toDTO(e);
    }

    public EvenementDTO createEvenement(EvenementDTO dto) {
        Evenement e = new Evenement();

        e.setTitre(dto.getTitre());
        e.setDescription(dto.getDescription());
        e.setDateEvent(java.time.LocalDate.parse(dto.getDate()));
        e.setPrix(dto.getPrix());

        e.setOrgaId(1); //TODO à modifier
        e.setAdminId(1); //TODO à modifier

        Evenement saved = repository.save(e);

        return toDTO(saved);
    }

    public void deleteEvenement(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Evenement not found");
        }
        repository.deleteById(id);
    }

    private EvenementDTO toDTO(Evenement e) {
        EvenementDTO dto = new EvenementDTO();

        dto.setId(e.getId());
        dto.setTitre(e.getTitre());
        dto.setDescription(e.getDescription());
        dto.setDate(e.getDateEvent().toString());
        dto.setPrix(e.getPrix());

        return dto;
    }
}