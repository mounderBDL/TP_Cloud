package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminDecisionRequest {

    @NotNull(message = "ID utilisateur obligatoire")
    private Long userId;

    @NotBlank(message = "Décision obligatoire (approve / reject)")
    private String decision;
}
