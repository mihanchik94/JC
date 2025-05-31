package ru.itk.spring_data.repository;

import ru.itk.spring_data.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Book save(Book book);
    List<Book> findAll();
    Optional<Book> findById(Long id);
    int update(Book book);
    int deleteById(Long id);
}
