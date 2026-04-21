package fr.univlyon1.m1if10.eventify_api.controller;

import fr.univlyon1.m1if10.eventify_api.dto.ParticipantDTO;
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
    @GetMapping("/{id}/participations")
    public List<ParticipantDTO> getParticipantsByEvent(@PathVariable int id) {
        return service.getParticipantsByEvent(id);
    }
}