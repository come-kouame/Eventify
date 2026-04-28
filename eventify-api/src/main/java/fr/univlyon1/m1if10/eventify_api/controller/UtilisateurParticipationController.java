package fr.univlyon1.m1if10.eventify_api.controller;

import fr.univlyon1.m1if10.eventify_api.dto.EvenementDTO;
import fr.univlyon1.m1if10.eventify_api.service.ParticipationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurParticipationController {

    private final ParticipationService service;

    public UtilisateurParticipationController(ParticipationService service) {
        this.service = service;
    }

    // GET /utilisateurs/{id}/participations
    @GetMapping("/{id}/participations")
    public List<EvenementDTO> getEvenements(@PathVariable Integer id) {
        return service.getEvenementsByUtilisateur(id);
    }
}