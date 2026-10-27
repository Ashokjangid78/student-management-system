package com.logic.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class CourseDTO {

    private Long id;

    @NotBlank(message = "Course name is required")
    @Size(max = 150 , message = "Max of 150 charactor allowed")
    private String courseName;

    @NotBlank(message = "Course code is required")
    private String courseCode;

    @NotBlank(message = "Course duration is required")
    private String duration;

    @NotNull(message = "Course fee is required")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "Course fee must be greater than zero")
    private BigDecimal fee;

    @Size(max = 500 , message = "Max of 500 charactor allowed")
    private String description;

    private Boolean active;

}
