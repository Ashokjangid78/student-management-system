package com.logic.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Data
public class EnrollmentSummaryDTO {
    private Long studentId;
    private String studentName;
    private String email;
    private int courseCount;
    private BigDecimal totalFee;
    private List<CourseDTO> courseList = new ArrayList<>();
}
