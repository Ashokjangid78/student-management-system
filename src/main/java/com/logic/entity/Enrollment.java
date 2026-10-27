package com.logic.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="enrollment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"student", "course"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="student_id", nullable = false)
    private Students student;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="course_id", nullable = false)
    private Courses course;

    @Column(nullable = false)
    private LocalDateTime enrolledDate = LocalDateTime.now();
}
