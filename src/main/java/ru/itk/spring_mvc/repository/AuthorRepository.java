package ru.itk.spring_mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itk.spring_mvc.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
