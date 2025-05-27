package ru.itk.spring_mvc.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class BookDto {

    @NotBlank(message = "Book title is mandatory")
    private String title;

    @Valid
    @NotNull(message = "Author must not be null")
    private AuthorDto author;

}
