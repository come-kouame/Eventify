package fr.univlyon1.m1if10.eventify_api.controller;

import fr.univlyon1.m1if10.eventify_api.dto.EvenementDTO;
import fr.univlyon1.m1if10.eventify_api.service.EvenementService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/evenements")
@CrossOrigin(origins = "*")
public class EvenementController {

    private final EvenementService service;

    public EvenementController(EvenementService service) {
        this.service = service;
    }


    // Récupérer tous les événements
    @GetMapping
    public List<EvenementDTO> getEvenements() {
        return service.getAllEvenements();
    }

    // Récupérer un événement par son ID
    @GetMapping("/{id}")
    public EvenementDTO getEvenementById(@PathVariable Integer id) {
        return service.getEvenementById(id);
    }

    // Créer un nouvel événement
    @PostMapping
    public EvenementDTO createEvenement(@RequestBody EvenementDTO dto) {
        return service.createEvenement(dto);
    }

    // Supprimer un événement par son ID
    @DeleteMapping("/{id}")
    public void deleteEvenement(@PathVariable Integer id) {
        service.deleteEvenement(id);
    }
}