package ru.itk.spring_mvc.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.itk.spring_mvc.dto.AuthorDto;
import ru.itk.spring_mvc.dto.BookDto;
import ru.itk.spring_mvc.mapper.BookMapper;
import ru.itk.spring_mvc.model.Author;
import ru.itk.spring_mvc.model.Book;
import ru.itk.spring_mvc.repository.BookRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BookServiceImpl bookService;


    @Test
    public void testFindAll() {
        Author author1 = Author.builder()
                .withId(1L)
                .withName("Author One")
                .build();

        Author author2 = Author.builder()
                .withId(2L)
                .withName("Author Two")
                .build();

        AuthorDto authorDto1 = AuthorDto.builder()
                .withAuthorId(1L)
                .withName("Author One")
                .build();

        AuthorDto authorDto2 = AuthorDto.builder()
                .withAuthorId(2L)
                .withName("Author Two")
                .build();

        Pageable pageable = PageRequest.of(0, 10);
        Book book1 = new Book(1L, "Book One", author1);
        Book book2 = new Book(2L, "Book Two", author2);
        List<Book> bookList = Arrays.asList(book1, book2);

        Page<Book> bookPage = new PageImpl<>(bookList, pageable, bookList.size());

        BookDto bookDto1 = new BookDto("Book One", authorDto1);
        BookDto bookDto2 = new BookDto("Book Two", authorDto2);

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.fromBookToBookDto(book1)).thenReturn(bookDto1);
        when(bookMapper.fromBookToBookDto(book2)).thenReturn(bookDto2);

        Page<BookDto> result = bookService.findAll(pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(bookDto1, result.getContent().get(0));
        assertEquals(bookDto2, result.getContent().get(1));

        verify(bookRepository, times(1)).findAll(pageable);
        verify(bookMapper, times(2)).fromBookToBookDto(any(Book.class));
    }

    @Test
    void testFindById() {
        Author author1 = Author.builder()
                .withId(1L)
                .withName("Author One")
                .build();

        AuthorDto authorDto1 = AuthorDto.builder()
                .withAuthorId(1L)
                .withName("Author One")
                .build();

        Long bookId = 1L;

        Book book = new Book(1L, "Book One", author1);
        BookDto bookDto = new BookDto("Book One", authorDto1);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.fromBookToBookDto(book)).thenReturn(bookDto);

        BookDto result = bookService.findById(bookId);

        assertNotNull(result);
        assertEquals(bookDto.getTitle(), result.getTitle());
        assertEquals(bookDto.getAuthor(), result.getAuthor());

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookMapper, times(1)).fromBookToBookDto(book);
    }

    @Test
    void testFindByIdBookNotFound() {
        Long bookId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.findById(bookId));

        assertEquals(String.format("Book with id: %d not found", bookId), exception.getMessage());

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookMapper, never()).fromBookToBookDto(any(Book.class));
    }

    @Test
    public void testSave_Success() {
        AuthorDto authorDto = AuthorDto.builder()
                .withAuthorId(1L)
                .withName("Author One")
                .build();

        Author author = Author.builder()
                .withId(1L)
                .withName("Author One")
                .build();

        Book book = Book.builder()
                .withId(1L)
                .withTitle("Book One")
                .withAuthor(author)
                .build();

        BookDto bookDto = BookDto.builder()
                .withTitle("Book One")
                .withAuthor(authorDto)
                .build();

        when(authorService.existById(1L)).thenReturn(true);
        when(bookMapper.fromBookDtoToBook(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.fromBookToBookDto(book)).thenReturn(bookDto);

        BookDto result = bookService.save(bookDto);

        assertNotNull(result);
        assertEquals(bookDto.getTitle(), result.getTitle());
        assertEquals(bookDto.getAuthor(), result.getAuthor());

        verify(authorService, times(1)).existById(1L);
        verify(bookMapper, times(1)).fromBookDtoToBook(bookDto);
        verify(bookRepository, times(1)).save(book);
        verify(bookMapper, times(1)).fromBookToBookDto(book);
    }

    @Test
    public void testSave_AuthorNotFound() {
        AuthorDto authorDto = AuthorDto.builder()
                .withAuthorId(1L)
                .withName("Author One")
                .build();

        BookDto bookDto = BookDto.builder()
                .withTitle("Book One")
                .withAuthor(authorDto)
                .build();

        when(authorService.existById(1L)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> bookService.save(bookDto));
        assertEquals("Author with id: 1 not found", exception.getMessage());

        verify(authorService, times(1)).existById(1L);
        verify(bookMapper, never()).fromBookDtoToBook(any(BookDto.class));
        verify(bookRepository, never()).save(any(Book.class));
        verify(bookMapper, never()).fromBookToBookDto(any(Book.class));
    }

    @Test
    public void testUpdateBook_Success() {
        Long bookId = 1L;

        AuthorDto authorDto = AuthorDto.builder()
                .withAuthorId(2L)
                .withName("Author Two")
                .build();

        Author newAuthor = Author.builder()
                .withId(2L)
                .withName("Author Two")
                .build();

        Book existingBook = Book.builder()
                .withId(bookId)
                .withTitle("Old Title")
                .withAuthor(Author.builder().withId(1L).withName("Author One").build())
                .build();

        Book updatedBook = Book.builder()
                .withId(bookId)
                .withTitle("New Title")
                .withAuthor(newAuthor)
                .build();

        BookDto bookDto = BookDto.builder()
                .withTitle("New Title")
                .withAuthor(authorDto)
                .build();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(authorService.findAuthorById(2L)).thenReturn(newAuthor);
        when(bookRepository.save(updatedBook)).thenReturn(updatedBook);
        when(bookMapper.fromBookToBookDto(updatedBook)).thenReturn(bookDto);

        BookDto result = bookService.updateBook(bookId, bookDto);

        assertNotNull(result);
        assertEquals(bookDto.getTitle(), result.getTitle());
        assertEquals(bookDto.getAuthor(), result.getAuthor());

        verify(bookRepository, times(1)).findById(bookId);
        verify(authorService, times(1)).findAuthorById(2L);
        verify(bookRepository, times(1)).save(updatedBook);
        verify(bookMapper, times(1)).fromBookToBookDto(updatedBook);
    }

    @Test
    public void testUpdateBook_AuthorNotFound() {
        Long bookId = 1L;

        AuthorDto authorDto = AuthorDto.builder()
                .withAuthorId(2L)
                .withName("Author Two")
                .build();

        BookDto bookDto = BookDto.builder()
                .withTitle("New Title")
                .withAuthor(authorDto)
                .build();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(new Book())); // Существующая книга
        when(authorService.findAuthorById(2L)).thenThrow(new EntityNotFoundException("Author with id: 2 not found"));

        Exception exception = assertThrows(EntityNotFoundException.class, () -> bookService.updateBook(bookId, bookDto));
        assertEquals("Author with id: 2 not found", exception.getMessage());

        verify(bookRepository, times(1)).findById(bookId);
        verify(authorService, times(1)).findAuthorById(2L);
        verify(bookRepository, never()).save(any(Book.class));
        verify(bookMapper, never()).fromBookToBookDto(any(Book.class));
    }

    @Test
    public void testUpdateBook_BookNotFound() {
        Long bookId = 1L;

        AuthorDto authorDto = AuthorDto.builder()
                .withAuthorId(1L)
                .withName("Author One")
                .build();

        BookDto bookDto = BookDto.builder()
                .withTitle("New Title")
                .withAuthor(authorDto)
                .build();

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> bookService.updateBook(bookId, bookDto));
        assertEquals("Book with id: 1 not found", exception.getMessage());

        verify(bookRepository, times(1)).findById(bookId);
        verify(authorService, never()).findAuthorById(anyLong());
        verify(bookRepository, never()).save(any(Book.class));
        verify(bookMapper, never()).fromBookToBookDto(any(Book.class));
    }

    @Test
    public void testDeleteBook() {
        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

}