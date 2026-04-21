package fr.univlyon1.m1if10.eventify_api.entity;
import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "participation", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"utilisateur_id", "evenement_id"})
})
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idParticipation;

    @Setter
    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @Setter
    @ManyToOne
    @JoinColumn(name = "evenement_id", nullable = false)
    private Evenement evenement;
}