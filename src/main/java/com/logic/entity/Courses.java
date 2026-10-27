package com.logic.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "enrollments")
@EqualsAndHashCode(exclude = "enrollments")
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String courseName;

    @Column(nullable = false,unique = true)
    private String courseCode;


    private String duration;

    @Column(name = "active",nullable = false)
    private boolean active = true;

    @Column(precision = 12 , scale = 2 , nullable = false)
    private BigDecimal fee;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false , updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Enrollment> enrollments = new HashSet<>();

//    @PrePersist
//    protected void onCreate() {
//      createdAt = LocalDateTime.now();
//    }

}
