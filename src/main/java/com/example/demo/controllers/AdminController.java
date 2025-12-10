package com.example.demo.controllers;


import com.example.demo.entities.Utilisateur;
import com.example.demo.repositories.UtilisateurRepository;
import com.example.demo.services.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private MailService mailService;


    // ============================
    // 1. Get all pending accounts
    // ============================
    @GetMapping("/pending")
    public List<Utilisateur> getPendingRequests() {
        return utilisateurRepository.findByStatutCompte("PENDING");
    }


    // ============================
    // 2. Approve an account
    // ============================
    @PostMapping("/approve/{id}")
    public String approveAccount(@PathVariable Long id) {

        Utilisateur user = utilisateurRepository.findById(id).orElse(null);

        if (user == null) {
            return "User not found";
        }

        user.setStatutCompte("APPROVED");
        utilisateurRepository.save(user);

        mailService.sendEmail(
                user.getEmail(),
                "Registration Approved",
                "Your account has been approved. You can now log in."
        );

        return "Account approved";
    }


    // ============================
    // 3. Reject an account
    // ============================
    @PostMapping("/reject/{id}")
    public String rejectAccount(@PathVariable Long id,
                                @RequestBody String comment) {

        Utilisateur user = utilisateurRepository.findById(id).orElse(null);

        if (user == null) {
            return "User not found";
        }

        user.setStatutCompte("REJECTED");
        utilisateurRepository.save(user);

        mailService.sendEmail(
                user.getEmail(),
                "Registration Rejected",
                "Your registration request was rejected.\n\nReason:\n" + comment
        );

        return "Account rejected";
    }
}

