package ru.itk.spring_data_projections.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "departments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
