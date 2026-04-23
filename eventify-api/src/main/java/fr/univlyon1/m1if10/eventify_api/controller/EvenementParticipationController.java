package fr.univlyon1.m1if10.eventify_api.controller;

import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurDTO;
import fr.univlyon1.m1if10.eventify_api.service.ParticipationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evenements")
@CrossOrigin(origins = "*")
public class EvenementParticipationController {

    private final ParticipationService service;

    public EvenementParticipationController(ParticipationService service) {
        this.service = service;
    }

    // GET /evenements/{id}/participations
    @GetMapping("/{id}/participants")
    public List<UtilisateurDTO> getParticipantsByEvent(@PathVariable int id) {
        return service.getParticipantsByEvent(id);
    }

    // Inscrire un utilisateur à un événement
    // GET /evenements/{id}/inscription?userId=1
    @GetMapping("/{id}/inscription")
    public void inscrireUtilisateur(@PathVariable int id, @RequestParam("userId") int userId) {
        service.inscrireUtilisateur(id, userId);
    }
}