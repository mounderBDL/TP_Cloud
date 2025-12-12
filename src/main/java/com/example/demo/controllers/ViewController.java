package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    // ======== AUTH PAGES ========

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";  // templates/auth/login.html
    }

    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";  // templates/auth/register.html
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "auth/forgot-password";  // templates/auth/forgot-password.html
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage() {
        return "auth/reset-password";  // templates/auth/reset-password.html
    }


    // ======== USER DASHBOARD ========

    @GetMapping("/user/dashboard")
    public String userDashboard() {
        return "user/dashboard";  // templates/user/dashboard.html
    }

    // ======== ADMIN PAGES ========

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";  // templates/admin/dashboard.html
    }

    @GetMapping("/admin/pending-requests")
    public String pendingRequestsPage() {
        return "admin/pending-requests";  // templates/admin/pending-requests.html
    }
}
