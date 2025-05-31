package ru.itk.spring_data.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.itk.spring_data.dto.BookDto;
import ru.itk.spring_data.model.Book;

import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {
    private final BookMapper mapper = Mappers.getMapper(BookMapper.class);

    @Test
    void testToDto() {
        Book book = Book.builder()
                .id(1L)
                .title("1984")
                .author("George Orwell")
                .year(Year.of(1949))
                .build();

        BookDto dto = mapper.toDto(book);

        assertNotNull(dto);
        assertEquals(dto.getTitle(), book.getTitle());
        assertEquals(dto.getAuthor(), book.getAuthor());
        assertEquals(dto.getYear(), book.getYear().getValue());
    }

    @Test
    void testToEntity() {
        BookDto dto = BookDto.builder()
                .title("To Kill a Mockingbird")
                .author("Harper Lee")
                .year(1960)
                .build();

        Book book = mapper.toEntity(dto);

        assertNotNull(dto);
        assertEquals(dto.getTitle(), book.getTitle());
        assertEquals(dto.getAuthor(), book.getAuthor());
        assertEquals(dto.getYear(), book.getYear().getValue());
    }

    @Test
    void testToBookDtoList() {
        Book book1 = Book.builder()
                .id(1L)
                .title("1984")
                .author("George Orwell")
                .year(Year.of(1949))
                .build();

        Book book2 = Book.builder()
                .id(2L)
                .title("Brave New World")
                .author("Aldous Huxley")
                .year(Year.of(1932))
                .build();

        List<Book> books = List.of(book1, book2);

        List<BookDto> dtoList = mapper.toBookDtoList(books);
        assertEquals(dtoList.size(), 2);

        for(int index = 0; index < dtoList.size(); index++) {
           BookDto dto = dtoList.get(index);
           Book book = books.get(index);
            assertEquals(dto.getTitle(), book.getTitle());
            assertEquals(dto.getAuthor(), book.getAuthor());
            assertEquals(dto.getYear(), book.getYear().getValue());
        }
    }

    @Test
    void testFromYearToInteger() {
        Year year = Year.of(2000);
        int yearInt = mapper.fromYearToInteger(year);
        assertEquals(yearInt, 2000);
    }

    @Test
    void testFromIntegerToYear() {
        int year = 1999;
        Year yearObj = mapper.fromIntegerToYear(year);
        assertEquals(yearObj, Year.of(year));
    }

}