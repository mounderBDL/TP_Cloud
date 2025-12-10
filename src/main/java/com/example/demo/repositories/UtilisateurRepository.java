package com.example.demo.repositories;


import com.example.demo.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    // Find user by email
    Optional<Utilisateur> findByEmail(String email);

    // Find users by account status (PENDING, APPROVED, REJECTED)
    List<Utilisateur> findByStatutCompte(String statutCompte);

    // For password reset
    Optional<Utilisateur> findByResetToken(String resetToken);
}


