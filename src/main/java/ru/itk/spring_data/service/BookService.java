package ru.itk.spring_data.service;

import ru.itk.spring_data.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto save(BookDto bookDto);
    List<BookDto> findAll();
    BookDto findById(Long id);
    BookDto update(Long id, BookDto bookDto);
    void delete(Long id);

}
