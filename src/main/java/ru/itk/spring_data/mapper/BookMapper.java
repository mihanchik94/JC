package ru.itk.spring_data.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.itk.spring_data.dto.BookDto;
import ru.itk.spring_data.model.Book;

import java.time.Year;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface BookMapper {

    @Mapping(target = "year", expression = "java(fromYearToInteger(book.getYear()))")
    BookDto toDto(Book book);

    @Mapping(target = "year", expression = "java(fromIntegerToYear(bookDto.getYear()))")
    Book toEntity(BookDto bookDto);

    List<BookDto> toBookDtoList(List<Book> books);

    default int fromYearToInteger(Year year) {
        return year.getValue();
    }

    default Year fromIntegerToYear(int year) {
        return Year.of(year);
    }

}
