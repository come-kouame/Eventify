package fr.univlyon1.m1if10.eventify_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "administrateur")
public class Administrateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_admin")
    private Integer id;

    @Column(name = "organisateur_id", nullable = false, unique = true)
    private Integer organisateurId;

    @Column(name = "utilisateur_id", nullable = false)
    private Integer utilisateurId;
}
