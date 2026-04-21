package fr.univlyon1.m1if10.eventify_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Getter
    @Setter
    @Column(name = "prenom", nullable = false, length = 50)
    private String prenom;

    @Getter
    @Setter
    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Getter
    @Setter
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Getter
    @Setter
    @Column(name = "ville", nullable = false, length = 100)
    private String ville;

    @Getter
    @Setter
    @Column(name = "rue", nullable = false, length = 150)
    private String rue;

    @Getter
    @Setter
    @Column(name = "code_postal", nullable = false, length = 10)
    private String codePostal;

    @Getter
    @Setter
    @Column(name = "login", nullable = false, unique = true, length = 50)
    private String login;

    @Getter
    @Setter
    @Column(name = "mdp", nullable = false, length = 100)
    private String mdp;
}