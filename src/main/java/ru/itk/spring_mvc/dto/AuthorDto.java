package ru.itk.spring_mvc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class AuthorDto {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Author id is mandatory")
    @Positive(message = "Author id must be positive")
    private Long authorId;

    private String name;
}
