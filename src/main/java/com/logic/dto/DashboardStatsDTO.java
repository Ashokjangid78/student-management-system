package com.logic.dto;

import lombok.Data;

@Data
public class DashboardStatsDTO {
    private long totalStudents;
    private long totalCourses;
    private String topPerformingCourse;
    private long studentsEnrolledThisMonth;
}
