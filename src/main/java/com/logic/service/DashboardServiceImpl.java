package com.logic.service;

import com.logic.dto.DashboardStatsDTO;
import com.logic.repository.CourseRepository;
import com.logic.repository.EnrollmentRepository;
import com.logic.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;


    @Override
    public DashboardStatsDTO getDashboardStats() {
        long totalStudents = studentRepository.count();
        long totalCourse = courseRepository.count();
        String topPerformingCourse = getTopPerformingCourse();

        YearMonth currentMonth = YearMonth.now();
        LocalDateTime startDate = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = currentMonth.atEndOfMonth().atTime(LocalTime.MAX);

        long studentEnrolledThisMonth = enrollmentRepository.countDistinctStudentByEnrollDateBetween(startDate, endDate);

        DashboardStatsDTO dashboardStatsDTO = new DashboardStatsDTO();
        dashboardStatsDTO.setTotalStudents(totalStudents);
        dashboardStatsDTO.setTotalCourses(totalCourse);
        dashboardStatsDTO.setTopPerformingCourse(topPerformingCourse);
        dashboardStatsDTO.setStudentsEnrolledThisMonth(studentEnrolledThisMonth);

        return dashboardStatsDTO;
    }

    private String getTopPerformingCourse() {

        return enrollmentRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(e -> e.getCourse().getCourseName(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue()) //(java, 6) (angular, 5) (react, 2)
                .map(Map.Entry::getKey)
                .orElse("N/A");

    }
}
