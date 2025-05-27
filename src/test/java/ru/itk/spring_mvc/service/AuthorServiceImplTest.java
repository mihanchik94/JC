package ru.itk.spring_mvc.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itk.spring_mvc.model.Author;
import ru.itk.spring_mvc.repository.AuthorRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    void testExistByIdExists() {
        Long authorId = 1L;

        when(authorRepository.existsById(authorId)).thenReturn(true);

        boolean exists = authorService.existById(authorId);

        assertTrue(exists);
        verify(authorRepository, times(1)).existsById(authorId);
    }

    @Test
    void testExistByIdNotExists() {
        Long authorId = 1L;

        when(authorRepository.existsById(authorId)).thenReturn(false);

        boolean exists = authorService.existById(authorId);

        assertFalse(exists);
        verify(authorRepository, times(1)).existsById(authorId);
    }

    @Test
    void testFindAuthorById() {
        Long authorId = 1L;

        Author author = Author.builder()
                .withId(authorId)
                .withName("Author One")
                .build();

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        Author foundAuthor = authorService.findAuthorById(authorId);

        assertNotNull(foundAuthor);
        assertEquals(authorId, foundAuthor.getId());
        assertEquals(author.getName(), foundAuthor.getName());
        verify(authorRepository, times(1)).findById(authorId);
    }

    @Test
    void testFindAuthorByIdNotFound() {
        Long authorId = 1L;

        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> authorService.findAuthorById(authorId));
        assertEquals("Author with id: 1 not found", exception.getMessage());
        verify(authorRepository, times(1)).findById(authorId);
    }

}