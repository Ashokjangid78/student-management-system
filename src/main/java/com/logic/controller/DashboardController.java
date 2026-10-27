
package com.logic.controller;

import com.logic.service.DashboardService;
import com.logic.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class DashboardController {
    private final EnrollmentService enrollmentService;
    private final DashboardService dashboardService;

//    @GetMapping("/dashboard")
//    public String dashboard(Model model) {
//
//        Map<String, Object> dashboardStats = Map.of(
//                "totalStudents", 0,
//                "totalCourses", 0,
//                "topPerformingCourse", "N/A",
//                "studentsEnrolledThisMonth", 0
//        );
//
//        model.addAttribute("dashboardStats", dashboardStats);
//        model.addAttribute("students", List.of());
//
//        return "dashboard";
//    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("dashboardStats", dashboardService.getDashboardStats());
        model.addAttribute("students", enrollmentService.getRecentlyEnrolledStudents());
        return "dashboard";
    }
}
