package com.logic.service;

import com.logic.dto.CourseDTO;
import com.logic.entity.Courses;
import com.logic.repository.CourseRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;
    private final ModelMapper mapper;

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {
        log.info("creating course with code : {} ", courseDTO.getCourseCode());
        Courses courses = mapper.map(courseDTO , Courses.class);
        courses.setCreatedAt(LocalDateTime.now()); // Add this line
        courseRepository.save(courses);

        return mapper.map(courses, CourseDTO.class);
    }

    @Override
    public boolean existsByCourseCode(String code) {
        log.info("checking if code exists : {} ", code);
        return courseRepository.existsByCourseCodeIgnoreCase(code);
    }

    @Override
    public Page<CourseDTO> getCourses(int page, int size) {
        log.info("Getting courses from page: {}", page);

        int safePage = Math.max(0, page);
        int safeSize = Math.max(1, Math.min(size, 100));

        PageRequest pageRequest = PageRequest.of(
                safePage,
                safeSize,
                Sort.by(Sort.Direction.DESC, "id")
        );

        return courseRepository.findByActiveTrue(pageRequest)
                .map(course -> mapper.map(course, CourseDTO.class));
    }


    @Override
    @Transactional(readOnly = true)
    public CourseDTO getCourseById(Long id) {
       Courses courses = courseRepository.findById(id)
               .orElseThrow(()-> new RuntimeException("No course found"));
        return mapper.map(courses,CourseDTO.class);
    }

    @Override
    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        Courses course = courseRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No course found"));

        mapper.map(courseDTO , course);
        Courses updated =  courseRepository.save(course);


        return mapper.map(updated,CourseDTO.class);
    }

    @Override
    public boolean existsByCourseCodeAndIdNot(String code, Long id) {

        log.info("code from update page : {} ,id: {} ",code,id);
        return courseRepository.existsByCourseCodeIgnoreCaseAndIdNot(code, id);
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findByActiveTrue(Sort.by("courseName")).stream()
                .map(course -> mapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());
    }
}
