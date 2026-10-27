package com.logic.controller;

import com.logic.dto.EnrollmentDTO;
import com.logic.dto.EnrollmentSummaryDTO;
import com.logic.service.CourseService;
import com.logic.service.EnrollmentService;
import com.logic.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/enrollments")
@Slf4j
@RequiredArgsConstructor
public class EnrollmentController {
    private final CourseService courseService;
    private final StudentService studentService;
    private final EnrollmentService enrollmentService;

    @GetMapping("/showEnroll")
    public String showEnroll(Model model){
        log.info("Get /enrollment/showEnroll - showing enrollment page.");
        model.addAttribute("enrollmentDto",new EnrollmentDTO());
        model.addAttribute("courseList",courseService.getAllCourses());
        model.addAttribute("studentList",studentService.getAllStudents());
        return "enroll-course";
    }


    @PostMapping("/enrollCourse")
    public String enrollCourse(@Valid @ModelAttribute("enrollmentDto") EnrollmentDTO enrollmentDTO,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        log.info("Post /enrollments/enrollCourse - enrollment request received.");

        if(bindingResult.hasErrors()) {
            model.addAttribute("courseList", courseService.getAllCourses());
            model.addAttribute("studentList", studentService.getAllStudents());
            return "enroll-course";
        }

        enrollmentService.enrollStudentToCourses(enrollmentDTO);
        redirectAttributes.addAttribute("message", "Enrollment successfully!!");

        log.info("Post /enrollments/enrollCourse - Enrollment successfully.");

        return "redirect:/enrollments/enrollmentList";

    }


    @GetMapping("/enrollmentList")
    public String enrollmentList(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "3") int size,
                                 Model model,
                                 @RequestParam(value="message", required = false) String message)
    {
        log.info("Get /enrollmentList - showing enrolled student list page");

        Page<EnrollmentSummaryDTO> students = enrollmentService.getEnrolledStudents(page, size);
        model.addAttribute("students", students);
        model.addAttribute("message", message);

        return "enrolled-students";
    }


    @GetMapping("/getStudentEnrollmentDetails/{id}")
    public String getStudentEnrollmentDetails(@PathVariable Long id, Model model,
                                              @RequestParam(defaultValue = "enrollments") String source) {

        EnrollmentSummaryDTO enrollmentSummaryDTO
                = enrollmentService.findEnrolledStudentCourseDetails(id);

        model.addAttribute("enrollmentSummaryDTO", enrollmentSummaryDTO);
        model.addAttribute("source", source);

        return "enrollment-details";

    }


}
