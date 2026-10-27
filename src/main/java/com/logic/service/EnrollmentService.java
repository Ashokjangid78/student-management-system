package com.logic.service;

import com.logic.dto.EnrollmentDTO;
import com.logic.dto.EnrollmentSummaryDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EnrollmentService {
    void enrollStudentToCourses(EnrollmentDTO enrollmentDTO);

    Page<EnrollmentSummaryDTO> getEnrolledStudents(int page, int size);

    EnrollmentSummaryDTO findEnrolledStudentCourseDetails(Long studentId);

    List<EnrollmentSummaryDTO> getRecentlyEnrolledStudents();

}
