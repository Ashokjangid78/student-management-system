package com.logic.controller;

import com.logic.dto.CourseDTO;
import com.logic.repository.CourseRepository;
import com.logic.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseRepository courseRepository;
    private final CourseService courseService;

    @GetMapping("/new")
    public String showCreateCourse(Model model) {
        log.info("Get /course/new - showing create course page.");
        model.addAttribute("courseDto", new CourseDTO());
        return "add-course";
    }


@GetMapping("/list")
public String listCourse(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int size,
        Model model) {
    log.info("Get /course/list - showing course list page.");


    Page<CourseDTO> courses =
            courseService.getCourses(page, size);

    model.addAttribute("courses", courses);

    return "courses";
}


    @PostMapping
    public String createCourses(
            @Valid @ModelAttribute("courseDto") CourseDTO courseDto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        log.info("post /course - create course request received");

        if (bindingResult.hasErrors()) {
            log.info("post /course - page return due to validation erron.");
            return "add-course";
        }

        if (courseService.existsByCourseCode(courseDto.getCourseCode())) {
            log.error("post /course - course must be unique.");
            bindingResult.rejectValue(
                    "courseCode",
                    "duplicate",
                    "Course code must be unique"
            );
            return "add-course";
        }

        log.info("post /course - create course request recived.");

        courseService.createCourse(courseDto);

        redirectAttributes.addFlashAttribute(
                "message",
                "Course created successfully"
        );

        log.info("post /course - create course successfully created.");
        return "redirect:/course/list";
    }

    @GetMapping("/{id}")
    public String getCourseById(@PathVariable Long id, Model model){
      CourseDTO course =  courseService.getCourseById(id);
      model.addAttribute("course",course);
      return "view-course";
    }


    @GetMapping("{id}/edit")
    public String editCourse(@PathVariable Long id , Model model){
        CourseDTO course = courseService.getCourseById(id);
        model.addAttribute("courseDto",course);
        return "edit-course";
    }

    @PostMapping("{id}/update")
    public String updateCourse(@PathVariable Long id,
                               @Valid @ModelAttribute("courseDto") CourseDTO courseDto,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes){

    log.info("Post /{id}/update - update course requset recived .{} ",id);

        if (bindingResult.hasErrors()) {
            log.info("post /{id}/update - page return due to validation erron.");
            return "edit-course";
        }

        if (courseService.existsByCourseCodeAndIdNot(courseDto.getCourseCode(),id)) {
            log.error("post /{id}/update - course must be unique.");
            bindingResult.rejectValue(
                    "courseCode",
                    "duplicate",
                    "Course code must be unique"
            );
            return "edit-course";
        }
        courseService.updateCourse(id ,courseDto);

        redirectAttributes.addFlashAttribute(
                "message",
                "Course updated successfully"
        );

        log.info("post /{id}/update - update course successfully created.");
        return "redirect:/course/list";

    }

}
