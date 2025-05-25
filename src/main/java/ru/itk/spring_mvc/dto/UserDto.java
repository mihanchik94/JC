package ru.itk.spring_mvc.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import ru.itk.spring_mvc.view.Views;

import java.io.Serializable;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class UserDto implements Serializable {

    @JsonView(Views.UserSummary.class)
    @NotBlank(message = "Name is mandatory!")
    private String name;

    @JsonView(Views.UserSummary.class)
    @NotBlank(message = "Surname is mandatory!")
    private String surname;

    @JsonView(Views.UserSummary.class)
    @NotBlank(message = "Email is mandatory!")
    @Email(message = "Invalid email")
    private String email;

    @JsonView(Views.UserDetails.class)
    private List<OrderDto> orders;
}
