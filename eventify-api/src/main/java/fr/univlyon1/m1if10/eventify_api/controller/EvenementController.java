package fr.univlyon1.m1if10.eventify_api.controller;

import fr.univlyon1.m1if10.eventify_api.entity.Evenement;
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
    public List<Evenement> getEvenements() {
        return service.getAllEvenements();
    }
    /*
    // Récupérer un événement par son ID
    @GetMapping("/{id}")
    public Evenement getEvenementById(@PathVariable Long id) {
        return service.getEvenementById(id);
    }

    // Créer un nouvel événement
    @PostMapping
    public Evenement createEvenement(@RequestBody Evenement evenement) {
        return service.createEvenement(evenement);
    }

    // Supprimer un événement par son ID
    @DeleteMapping("/{id}")
    public void deleteEvenement(@PathVariable Long id) {
        service.deleteEvenement(id);
    }*/
}