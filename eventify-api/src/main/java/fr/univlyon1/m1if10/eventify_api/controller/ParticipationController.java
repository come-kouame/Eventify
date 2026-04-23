package fr.univlyon1.m1if10.eventify_api.controller;

import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurDTO;
import fr.univlyon1.m1if10.eventify_api.entity.Participation;
import fr.univlyon1.m1if10.eventify_api.service.ParticipationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/participations")
public class ParticipationController {

    private final ParticipationService service;

    public ParticipationController(ParticipationService service) {
        this.service = service;
    }

    // S'inscrire à un événement
    @PostMapping
    public void participer(@RequestParam Integer utilisateurId,
                           @RequestParam Integer evenementId) {
        service.participer(utilisateurId, evenementId);
    }

    // Voir les participants d’un événement
    @GetMapping("/evenement/{id}")
    public List<UtilisateurDTO> getParticipants(@PathVariable Integer id) {
        return service.getParticipantsByEvent(id);
    }
}