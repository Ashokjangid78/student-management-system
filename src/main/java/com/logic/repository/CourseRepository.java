package com.logic.repository;

import com.logic.entity.Courses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Courses , Long> {
    boolean existsByCourseCodeIgnoreCase(String courseCode);

    boolean existsByCourseCodeIgnoreCaseAndIdNot(String courseCode, Long id);

     Page<Courses> findByActiveTrue(Pageable pageable);

    List<Courses> findByActiveTrue(Sort sort);
}
