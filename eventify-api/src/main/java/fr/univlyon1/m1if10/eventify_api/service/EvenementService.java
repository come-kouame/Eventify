package fr.univlyon1.m1if10.eventify_api.service;

import fr.univlyon1.m1if10.eventify_api.dto.EvenementDTO;
import fr.univlyon1.m1if10.eventify_api.entity.Evenement;
import fr.univlyon1.m1if10.eventify_api.repository.EvenementRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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

    public List<EvenementDTO> getValidEvenements() {
        return repository.findAll().stream()
                .filter(e -> e.isValide() && e.getDate_debut().isAfter(LocalDateTime.now()))
                .map(this::toDTO)
                .toList();
    }

    public EvenementDTO getEvenementById(Integer id) {
        Evenement e = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Événement non trouvé " + id));

        return toDTO(e);
    }

    public EvenementDTO createEvenement(EvenementDTO dto) {
        Evenement e = new Evenement();

        e.setTitre(dto.getTitre());
        e.setDescription(dto.getDescription());
        e.setDate_debut(dto.getDate_debut());
        e.setDate_fin(dto.getDate_fin());
        e.setLieu(dto.getLieu());
        e.setAdresse(dto.getAdresse());
        e.setPoster(dto.getPoster());
        e.setPrix(dto.getPrix());
        e.setNb_places(dto.getNb_places());
        e.setNb_places_vendues(dto.getNb_places_vendues());
        e.setNb_places_vendues(dto.getNb_places_vendues());
        e.setOrgaId(dto.getOrga_id());

        Evenement saved = repository.save(e);

        return toDTO(saved);
    }

    public EvenementDTO editEvenement(Integer id, EvenementDTO dto) {
        Evenement e = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evenement non trouvé"));

        e.setTitre(dto.getTitre());
        e.setDescription(dto.getDescription());
        e.setDate_debut(dto.getDate_debut());
        e.setDate_fin(dto.getDate_fin());
        e.setLieu(dto.getLieu());
        e.setAdresse(dto.getAdresse());
        e.setPoster(dto.getPoster());
        e.setPrix(dto.getPrix());
        e.setNb_places(dto.getNb_places());
        e.setNb_places_vendues(dto.getNb_places_vendues());
        e.setValide(dto.isValide());
        e.setOrgaId(dto.getOrga_id());
        e.setAdminId(dto.getAdmin_id());

        Evenement updated = repository.save(e);

        return toDTO(updated);
    }

    public EvenementDTO validateEvenement(Integer id) {
        Evenement e = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evenement non trouvé"));

        if (e.isValide()) {

            throw new RuntimeException("Evenement déjà validé");
        }

        e.setValide(true);

        Evenement updated = repository.save(e);

        return toDTO(updated);
    }

    public void deleteEvenement(Integer id) {
        repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Événement non trouvé " + id));
        repository.deleteById(id);
    }

    private EvenementDTO toDTO(Evenement e) {
        EvenementDTO dto = new EvenementDTO();

        dto.setId(e.getId());
        dto.setTitre(e.getTitre());
        dto.setDescription(e.getDescription());
        dto.setDate_debut(e.getDate_debut());
        dto.setDate_fin(e.getDate_fin());
        dto.setLieu(e.getLieu());
        dto.setAdresse(e.getAdresse());
        dto.setPoster(e.getPoster());
        dto.setPrix(e.getPrix());
        dto.setNb_places(e.getNb_places());
        dto.setNb_places_vendues(e.getNb_places_vendues());
        dto.setValide(e.isValide());
        dto.setOrga_id(e.getOrgaId());
        dto.setAdmin_id(e.getAdminId());

        return dto;
    }
}