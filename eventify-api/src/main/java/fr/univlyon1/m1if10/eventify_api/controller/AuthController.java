package fr.univlyon1.m1if10.eventify_api.controller;

import fr.univlyon1.m1if10.eventify_api.dto.AuthResponseDTO;
import fr.univlyon1.m1if10.eventify_api.dto.LoginDTO;
import fr.univlyon1.m1if10.eventify_api.dto.UtilisateurDTO;
import fr.univlyon1.m1if10.eventify_api.service.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {
    private final UtilisateurService service;

    public AuthController(UtilisateurService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginDTO dto, HttpServletRequest request, HttpServletResponse response) {
        return service.login(dto, request);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    @GetMapping("/me")
    public UtilisateurDTO me(HttpServletRequest request) {
        return service.getCurrentUserDTO(request);
    }
}
