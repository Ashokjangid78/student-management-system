package com.logic.service;

import com.logic.dto.CourseDTO;
import com.logic.dto.StudentDTO;
import com.logic.entity.Courses;
import com.logic.entity.Students;
import com.logic.repository.StudentRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        log.info("email from create student");
        return studentRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id) {
        log.info("email from update student ");
        return studentRepository.existsByEmailIgnoreCaseAndIdNot(email,id);
    }

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        log.info("saving student data ");
        Students students = mapper.map(studentDTO,Students.class);
        Students saved = studentRepository.save(students);
        return mapper.map(saved,StudentDTO.class);
    }



    @Override
    public Page<StudentDTO> getStudents(int page, int size) {
        log.info("list of student from: {}", page);

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return studentRepository.findByActiveTrue(pageRequest)
                .map(student -> mapper.map(student, StudentDTO.class));
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDTO getStudentById(Long id) {
        Students student = studentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No course found"));
        return mapper.map(student, StudentDTO.class);
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Students student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No student found"));

        mapper.map(studentDTO, student);

        Students updated = studentRepository.save(student);

        return mapper.map(updated, StudentDTO.class);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findByActiveTrue().stream()
                .map(student -> mapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());
    }



}