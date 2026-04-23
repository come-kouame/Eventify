package fr.univlyon1.m1if10.eventify_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "utilisateur")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Column(name = "prenom", nullable = false, length = 50)
    private String prenom;

    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "ville", nullable = false, length = 100)
    private String ville;

    @Column(name = "rue", nullable = false, length = 150)
    private String rue;

    @Column(name = "code_postal", nullable = false, length = 10)
    private String codePostal;

    @Column(name = "login", nullable = false, unique = true, length = 50)
    private String login;

    @JsonIgnore
    @Column(name = "mdp", nullable = false, length = 100)
    private String mdp;
}