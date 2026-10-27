package com.logic.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EnrollmentDTO {
    @NotNull(message = "Student is required")
    private Long studentId;

    @NotEmpty(message = "Select at least one course")
    private List<Long> courseIds = new ArrayList<>();

}
