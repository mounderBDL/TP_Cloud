package com.example.demo.controllers;


import com.example.demo.dto.*;
import com.example.demo.entities.Utilisateur;
import com.example.demo.repositories.UtilisateurRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.services.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MailService mailService;


    // ============================
    // 1. Registration Request
    // ============================
    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterRequest request) {

        if (utilisateurRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists";
        }

        Utilisateur user = new Utilisateur();
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setEmail(request.getEmail());
        user.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        user.setRole(request.getRole());
        user.setStatutCompte("PENDING");

        utilisateurRepository.save(user);

        return "Registration request submitted successfully";
    }


    // ============================
    // 2. Login
    // ============================
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getMotDePasse())
        );

        Utilisateur user = utilisateurRepository.findByEmail(request.getEmail()).get();

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return new LoginResponse(token, user.getRole(), user.getNom(), user.getPrenom());
    }


    // ============================
    // 3. Forgot Password
    // ============================
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody ForgotPasswordRequest request) {

        Utilisateur user = utilisateurRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            return "Email not found";
        }

        String resetToken = UUID.randomUUID().toString();

        user.setResetToken(resetToken);
        user.setResetTokenExpiration(LocalDateTime.now().plusHours(1));

        utilisateurRepository.save(user);

        String link = "http://localhost:4200/reset-password/" + resetToken;

        mailService.sendEmail(
                user.getEmail(),
                "Password Reset Request",
                "Click the following link to reset your password:\n" + link
        );

        return "Password reset link sent to your email";
    }


    // ============================
    // 4. Reset Password
    // ============================
    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody ResetPasswordRequest request) {

        Utilisateur user = utilisateurRepository.findByResetToken(request.getToken())
                .orElse(null);

        if (user == null || user.getResetTokenExpiration().isBefore(LocalDateTime.now())) {
            return "Invalid or expired token";
        }

        user.setMotDePasse(passwordEncoder.encode(request.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiration(null);

        utilisateurRepository.save(user);

        return "Password updated successfully";
    }
}

