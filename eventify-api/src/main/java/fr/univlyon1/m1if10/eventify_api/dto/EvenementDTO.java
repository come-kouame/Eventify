package fr.univlyon1.m1if10.eventify_api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EvenementDTO {
    private Integer id;
    private String titre;
    private String description;
    private LocalDateTime date_debut;
    private LocalDateTime date_fin;
    private String lieu;
    private String adresse;
    private String poster;
    private Double prix;
    private Integer nb_places;
    private Integer nb_places_vendues;
    private Boolean valide;
    private Integer orga_id;
    private Integer admin_id;

    public boolean isValide() {
        return this.valide;
    }
}
