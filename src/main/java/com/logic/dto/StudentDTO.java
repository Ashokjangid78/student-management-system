package com.logic.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentDTO {
    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "last name is required")
    private String lastName;

    @NotBlank(message = "email is required")
    private String email;

    private String phoneNumber;

    @Size(max = 500,message = "Address must be within 500 characters")
    private String address;

    private boolean active = true;


}
