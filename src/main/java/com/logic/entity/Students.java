package com.logic.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "enrollments")
@EqualsAndHashCode(exclude = "enrollments")
public class Students {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false,unique = true)
    private String email;

    private String phoneNumber;

    @Column(length = 500)
    private String address;

    @Column(name = "active" ,nullable = false)
    private boolean active = true;

    @Column(nullable = false , updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Enrollment> enrollments = new HashSet<>();

    @PrePersist
    protected void onCreate() {
     createdAt = LocalDateTime.now();
    }
}
