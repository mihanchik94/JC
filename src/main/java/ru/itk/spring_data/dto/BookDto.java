package ru.itk.spring_data.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    private String title;

    private String author;

    private Integer year;
}
