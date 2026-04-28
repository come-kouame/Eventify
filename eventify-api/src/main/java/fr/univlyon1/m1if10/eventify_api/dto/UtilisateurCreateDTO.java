package fr.univlyon1.m1if10.eventify_api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UtilisateurCreateDTO {
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String email;
    private String ville;
    private String rue;
    private String codePostal;
    private String mdp;
}
