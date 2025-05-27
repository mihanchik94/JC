package ru.itk.spring_mvc.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.itk.spring_mvc.dto.BookDto;
import ru.itk.spring_mvc.model.Book;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true),
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

    @Mapping(source = "author.id", target = "author.authorId")
    @Mapping(source = "author.name", target = "author.name")
    BookDto fromBookToBookDto(Book book);

    @Mapping(source = "author.authorId", target = "author.id")
    @Mapping(source = "author.name", target = "author.name")
    Book fromBookDtoToBook(BookDto bookDto);
}
