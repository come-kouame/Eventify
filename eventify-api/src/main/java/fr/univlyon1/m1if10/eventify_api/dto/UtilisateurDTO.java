package fr.univlyon1.m1if10.eventify_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UtilisateurDTO {

    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String ville;
}