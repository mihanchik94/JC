package ru.itk.spring_mvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.itk.spring_mvc.dto.OrderDto;
import ru.itk.spring_mvc.dto.UserDto;
import ru.itk.spring_mvc.service.UserService;
import ru.itk.spring_mvc.view.Views;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private UserDto userDto1, userDto2;
    private List<UserDto> userDtoList;

    @BeforeEach
    void init() {
        userDto1 = new UserDto();
        userDto1.setName("John");
        userDto1.setSurname("Doe");
        userDto1.setEmail("john.doe@example.com");
        userDto1.setOrders(List.of(new OrderDto()));

        userDto2 = new UserDto();
        userDto2.setName("Jane");
        userDto2.setSurname("Smith");
        userDto2.setEmail("jane.smith@example.com");
        userDto2.setOrders(List.of(new OrderDto()));

        userDtoList = Arrays.asList(userDto1, userDto2);
    }

    @Test
    @SneakyThrows
    void testGetAllUsersWithUsersReturnsOkWithUserSummaryView() {
        when(userService.findAll()).thenReturn(userDtoList);

        mockMvc.perform(get("/user/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(userDto1.getName()))
                .andExpect(jsonPath("$[0].surname").value(userDto1.getSurname()))
                .andExpect(jsonPath("$[0].email").value(userDto1.getEmail()))
                .andExpect(jsonPath("$[0].orders").doesNotExist())
                .andExpect(jsonPath("$[1].name").value(userDto2.getName()))
                .andExpect(jsonPath("$[1].surname").value(userDto2.getSurname()))
                .andExpect(jsonPath("$[1].email").value(userDto2.getEmail()))
                .andExpect(jsonPath("$[1].orders").doesNotExist());
    }

    @Test
    @SneakyThrows
    void testGetUserByIdWithExistingIdReturnsOkWithUserDetailsView() {
        when(userService.findById(eq(1L))).thenReturn(userDto1);

        mockMvc.perform(get("/user/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.orders").isArray())
                .andExpect(jsonPath("$.orders[0]").exists());
    }

    @Test
    @SneakyThrows
    void testCreateUserWithValidUserDtoReturnsCreatedWithUserSummaryView() {
        when(userService.create(any(UserDto.class))).thenReturn(userDto1);


        String json = objectMapper.writerWithView(Views.UserSummary.class).writeValueAsString(userDto1);

        mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(userDto1.getName()))
                .andExpect(jsonPath("$.surname").value(userDto1.getSurname()))
                .andExpect(jsonPath("$.email").value(userDto1.getEmail()))
                .andExpect(jsonPath("$.orders").doesNotExist());
    }

    @Test
    @SneakyThrows
    void testUpdateUserWithValidUserDtoAndExistingIdReturnsOkWithUserSummaryView() {
        when(userService.update(eq(1L), any(UserDto.class))).thenReturn(userDto1);

        String json = objectMapper.writerWithView(Views.UserSummary.class).writeValueAsString(userDto1);

        mockMvc.perform(put("/user/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userDto1.getName()))
                .andExpect(jsonPath("$.surname").value(userDto1.getSurname()))
                .andExpect(jsonPath("$.email").value(userDto1.getEmail()))
                .andExpect(jsonPath("$.orders").doesNotExist());
    }

    @Test
    @SneakyThrows
    void testDeleteUser_WithExistingId_ReturnsOk() {
        mockMvc.perform(delete("/user/{id}", 1L))
                .andExpect(status().isOk());
        verify(userService, times(1)).delete(eq(1L));
    }
}
