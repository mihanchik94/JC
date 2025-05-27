package ru.itk.spring_mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itk.spring_mvc.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
