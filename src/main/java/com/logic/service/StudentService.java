package com.logic.service;

import com.logic.dto.StudentDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

    StudentDTO createStudent(StudentDTO studentDTO);

    Page<StudentDTO> getStudents(int page, int size);

    StudentDTO getStudentById(Long id);

    StudentDTO updateStudent(Long id, StudentDTO studentDTO);

    List<StudentDTO> getAllStudents();

}
