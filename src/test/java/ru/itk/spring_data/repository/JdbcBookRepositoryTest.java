package ru.itk.spring_data.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.itk.spring_data.model.Book;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class JdbcBookRepositoryTest {
    @Autowired
    private JdbcBookRepository bookRepository;

    @Test
    public void testSave() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setYear(Year.of(2023));

        Book savedBook = bookRepository.save(book);
        assertNotNull(savedBook.getId());
        assertEquals("Test Book", savedBook.getTitle());
        assertEquals("Test Author", savedBook.getAuthor());
        assertEquals(Year.of(2023), savedBook.getYear());

        Optional<Book> retrievedBook = bookRepository.findById(savedBook.getId());
        assertTrue(retrievedBook.isPresent());
        assertEquals("Test Book", retrievedBook.get().getTitle());
    }

    @Test
    public void testFindAll() {
        List<Book> books = bookRepository.findAll();
        assertEquals(2, books.size());
        assertEquals("Book 1", books.get(0).getTitle());
        assertEquals("Book 2", books.get(1).getTitle());
    }

    @Test
    public void testFindById() {
        Optional<Book> book = bookRepository.findById(1L);
        assertTrue(book.isPresent());
        assertEquals("Book 1", book.get().getTitle());
        assertEquals("Author 1", book.get().getAuthor());
        assertEquals(Year.of(2021), book.get().getYear());
    }

    @Test
    public void testUpdate() {
        Optional<Book> book = bookRepository.findById(1L);
        assertTrue(book.isPresent());

        book.get().setTitle("Updated Title");
        int rowsAffected = bookRepository.update(book.get());
        assertEquals(1, rowsAffected);

        Optional<Book> updatedBook = bookRepository.findById(1L);
        assertTrue(updatedBook.isPresent());
        assertEquals("Updated Title", updatedBook.get().getTitle());
    }

    @Test
    @DirtiesContext
    public void testDeleteById() {
        int rowsAffected = bookRepository.deleteById(1L);
        assertEquals(1, rowsAffected);

        Optional<Book> book = bookRepository.findById(1L);
        assertFalse(book.isPresent());
    }

}