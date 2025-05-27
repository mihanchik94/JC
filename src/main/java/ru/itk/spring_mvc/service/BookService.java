package ru.itk.spring_mvc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.itk.spring_mvc.dto.BookDto;

public interface BookService {
    Page<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    BookDto save(BookDto bookDto);

    BookDto updateBook(Long id, BookDto bookDto);

    void deleteBook(Long id);
}
