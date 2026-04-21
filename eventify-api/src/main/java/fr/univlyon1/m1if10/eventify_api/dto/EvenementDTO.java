package fr.univlyon1.m1if10.eventify_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvenementDTO {
    private Integer id;
    private String titre;
    private String description;
    private String date;
    private Double prix;
}
