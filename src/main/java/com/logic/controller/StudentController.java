package com.logic.controller;

import com.logic.dto.CourseDTO;
import com.logic.dto.StudentDTO;
import com.logic.entity.Students;
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
@RequestMapping("/students")
@Slf4j
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/new")
    public String showCreateStudent(Model model){
        log.info("Get / new - showing create student page");
        model.addAttribute("studentDto",new StudentDTO());

        return "add-student";
    }

    @GetMapping("/list")
    public String listStudent(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "3") int size,
                              Model model,
                              @RequestParam(value="message", required = false) String message)
    {
        log.info("Get /list - showing list student page");

        Page<StudentDTO> students = studentService.getStudents(page, size);
        model.addAttribute("students", students);
        model.addAttribute("message", message);

        return "students";
    }
    @PostMapping("/save")
    public String createStudent(@Valid @ModelAttribute("studentDto") StudentDTO studentDTO,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes){

        log.info("Post / save - create student request received");
        if (bindingResult.hasErrors()){
            return "add-student";
        }
        if (studentService.existsByEmailIgnoreCase(studentDTO.getEmail())){
            log.info("Post / save - email must be unique");
            bindingResult.rejectValue(
                    "email",
                    "duplicate",
                    "email must be unique"
            );
            return "add-student";
        }
        studentService.createStudent(studentDTO);
        redirectAttributes.addAttribute("message","Student is added successfully!!");

        return "redirect:/students/list";
    }

    @GetMapping("/{id}")
    public String getStudentById(@PathVariable Long id , Model model){
        StudentDTO student = studentService.getStudentById(id);
        model.addAttribute("student",student);

        return "view-student";
    }

    @GetMapping("/{id}/edit")
    public String editStudent(@PathVariable Long id , Model model){
        StudentDTO student = studentService.getStudentById(id);
        model.addAttribute("studentDto",student);

        return "edit-student";
    }

    @PostMapping("/{id}/update")
    public String updateStudent(@PathVariable Long id,
                                @Valid @ModelAttribute("studentDto") StudentDTO studentDTO,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        log.info("Post /update - update student request received");

        if(bindingResult.hasErrors()) {
            return "edit-student";
        }

        if(studentService.existsByEmailIgnoreCaseAndIdNot(studentDTO.getEmail(), id)) {
            log.error("Post /update - email must be unique.");

            bindingResult.rejectValue("email", null, "email must be unique");

            return "edit-student";
        }


        studentService.updateStudent(id, studentDTO);
        redirectAttributes.addAttribute("message", "Student is updated successfully!!");

        return "redirect:/students/list";


    }

}
