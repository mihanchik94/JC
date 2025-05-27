package ru.itk.spring_mvc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itk.spring_mvc.dto.BookDto;
import ru.itk.spring_mvc.mapper.BookMapper;
import ru.itk.spring_mvc.model.Author;
import ru.itk.spring_mvc.model.Book;
import ru.itk.spring_mvc.repository.BookRepository;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorService authorService;

    @Override
    public Page<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::fromBookToBookDto);
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.fromBookToBookDto(findBookById(id));
    }

    @Override
    public BookDto save(BookDto bookDto) {
        checkAuthorExistence(bookDto.getAuthor().getAuthorId());
        Book book = bookMapper.fromBookDtoToBook(bookDto);
        return bookMapper.fromBookToBookDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    public BookDto updateBook(Long id, BookDto bookDto) {
        Book updatedBook = findBookById(id);
        Author newAuthor = authorService.findAuthorById(bookDto.getAuthor().getAuthorId());
        updatedBook.setTitle(bookDto.getTitle());
        updatedBook.setAuthor(newAuthor);
        return bookMapper.fromBookToBookDto(bookRepository.save(updatedBook));
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    private Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Book with id: %d not found", id)));
    }

    private void checkAuthorExistence(Long authorId) {
        if (!authorService.existById(authorId)) {
            throw new EntityNotFoundException(String.format("Author with id: %d not found", authorId));
        }
    }
}
