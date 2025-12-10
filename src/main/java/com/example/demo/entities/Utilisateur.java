package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Entity mapped to the "utilisateurs" table.
 * Uses Lombok to reduce boilerplate.
 */

@Entity
@Table(name = "utilisateurs")
@Data                       // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor          // Generates default constructor
@AllArgsConstructor         // Generates constructor with all fields
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "mot_de_passe", nullable = false, length = 255)
    private String motDePasse;

    @Column(nullable = false, length = 50)
    private String role; // ADMIN, GESTIONNAIRE, TRAVAILLEUR, ENSEIGNANT, ETUDIANT

    @Column(name = "statut_compte", nullable = false, length = 50)
    private String statutCompte; // PENDING, APPROVED, REJECTED

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "reset_token_expiration")
    private LocalDateTime resetTokenExpiration;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Automatically set createdAt when inserting row
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
