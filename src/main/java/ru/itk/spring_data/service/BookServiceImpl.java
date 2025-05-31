package ru.itk.spring_data.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itk.spring_data.dto.BookDto;
import ru.itk.spring_data.mapper.BookMapper;
import ru.itk.spring_data.model.Book;
import ru.itk.spring_data.repository.BookDao;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final BookMapper bookMapper;


    @Override
    public BookDto save(BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        return bookMapper.toDto(bookDao.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookMapper.toBookDtoList(bookDao.findAll());
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.toDto(findBookById(id));
    }

    @Override
    public BookDto update(Long id, BookDto bookDto) {
        Book updatedBook = bookMapper.toEntity(bookDto);
        updatedBook.setId(id);
        int affectedRows = bookDao.update(updatedBook);
        if (affectedRows == 0) {
            throw new EntityNotFoundException(String.format("Book with id: %d not found", id));
        }
        return bookDto;
    }

    @Override
    public void delete(Long id) {
        bookDao.deleteById(id);
    }

    private Book findBookById(Long id) {
        return bookDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Book with id: %d not found", id)));
    }
}
