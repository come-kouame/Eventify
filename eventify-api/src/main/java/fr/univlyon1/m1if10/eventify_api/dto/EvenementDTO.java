package fr.univlyon1.m1if10.eventify_api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EvenementDTO {
    private Integer id;
    private String titre;
    private String description;
    private LocalDate date_debut;
    private LocalDate date_fin;
    private String lieu;
    private String adresse;
    private String poster;
    private Double prix;
    private int nb_places;
    private int nb_places_vendues;
    private boolean valide;
    private int orga_id;
    private int admin_id;
}
