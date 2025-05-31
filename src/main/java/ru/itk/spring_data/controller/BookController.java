package ru.itk.spring_data.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itk.spring_data.dto.BookDto;
import ru.itk.spring_data.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/")
    public ResponseEntity<BookDto> saveBook(@RequestBody BookDto bookDto) {
        return new ResponseEntity<>(bookService.save(bookDto), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.findAll();
        return books.isEmpty()
                ? new ResponseEntity<>(books, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable("id") Long id, @RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.update(id, bookDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
