package ru.itk.spring_mvc.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itk.spring_mvc.dto.UserDto;
import ru.itk.spring_mvc.mapper.UserMapper;
import ru.itk.spring_mvc.model.User;
import ru.itk.spring_mvc.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testFindAllWhenUsersExistReturnsUserDtoList() {
        User user1 = User.builder()
                .withId(1L)
                .withName("John")
                .withEmail("john@example.com")
                .build();

        User user2 = User.builder()
                .withId(2L)
                .withName("Jane")
                .withEmail("jane@example.com")
                .build();

        UserDto userDto1 = UserDto.builder()
                .withName("John")
                .withEmail("john@example.com")
                .build();

        UserDto userDto2 = UserDto.builder()
                .withName("Jane")
                .withEmail("jane@example.com")
                .build();


        List<User> users = List.of(user1, user2);
        List<UserDto> userDtos = List.of(userDto1, userDto2);


        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.fromUserListToUserDtoList(users)).thenReturn(userDtos);

        List<UserDto> result = userService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(userDto1, result.get(0));
        assertEquals(userDto2, result.get(1));

        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).fromUserListToUserDtoList(users);
    }

    @Test
    public void testFindAllWhenNoUsersExistReturnsEmptyList() {
        List<User> emptyUserList = Collections.emptyList();
        List<UserDto> emptyUserDtoList = Collections.emptyList();

        when(userRepository.findAll()).thenReturn(emptyUserList);
        when(userMapper.fromUserListToUserDtoList(emptyUserList)).thenReturn(emptyUserDtoList);

        List<UserDto> result = userService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).fromUserListToUserDtoList(emptyUserList);
    }

    @Test
    public void testFindByIdUserExistsReturnsUserDto() {
        Long userId = 1L;
        User user = User.builder()
                .withId(userId)
                .withName("John")
                .withEmail("john@example.com")
                .build();

        UserDto userDto = UserDto.builder()
                .withName("John")
                .withEmail("john@example.com")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.fromUserToUserDto(user)).thenReturn(userDto);

        UserDto result = userService.findById(userId);

        assertNotNull(result);
        assertEquals(userDto, result);

        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, times(1)).fromUserToUserDto(user);
    }

    @Test
    public void testFindByIdUserDoesNotExistThrowsEntityNotFoundException() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.findById(userId));

        String expectedMessage = String.format("User with id = %d not found", userId);
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, never()).fromUserToUserDto(any(User.class));
    }

    @Test
    public void testCreateUserDtoReturnsCreatedUserDto() {
        UserDto userDto = UserDto.builder()
                .withName("John")
                .withEmail("john@example.com")
                .build();

        User userToSave = User.builder()
                .withName("John")
                .withEmail("john@example.com")
                .build();

        User savedUser = User.builder()
                .withId(1L)
                .withName("John")
                .withEmail("john@example.com")
                .build();

        when(userMapper.fromUserDtoToUser(userDto)).thenReturn(userToSave);
        when(userRepository.save(userToSave)).thenReturn(savedUser);
        when(userMapper.fromUserToUserDto(savedUser)).thenReturn(userDto);

        UserDto result = userService.create(userDto);

        assertNotNull(result);
        assertEquals(userDto, result);

        verify(userMapper, times(1)).fromUserDtoToUser(userDto);
        verify(userRepository, times(1)).save(userToSave);
        verify(userMapper, times(1)).fromUserToUserDto(savedUser);
    }

    @Test
    public void testUpdateUserExistsReturnsUpdatedUserDto() {
        Long userId = 1L;
        UserDto userDto = UserDto.builder()
                .withName("John")
                .withEmail("john@example.com")
                .build();

        User existingUser = User.builder()
                .withId(userId)
                .withName("Jane")
                .withEmail("jane@example.com")
                .build();

        User updatedUser = User.builder()
                .withId(userId)
                .withName(userDto.getName())
                .withEmail(userDto.getEmail())
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);
        when(userMapper.fromUserToUserDto(updatedUser)).thenReturn(userDto);

        UserDto result = userService.update(userId, userDto);

        assertNotNull(result);
        assertEquals(userDto.getName(), result.getName());
        assertEquals(userDto.getSurname(), result.getSurname());
        assertEquals(userDto.getEmail(), result.getEmail());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(updatedUser);
        verify(userMapper, times(1)).fromUserToUserDto(updatedUser);
    }


    @Test
    public void testUpdateUserNotExistsThrowsException() {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setName("NewName");
        userDto.setSurname("NewSurname");
        userDto.setEmail("newemail@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.update(userId, userDto));

        assertEquals("User with id = 1 not found", exception.getMessage());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(User.class));
        verify(userMapper, never()).fromUserToUserDto(any(User.class));
    }

    @Test
    public void testDeleteUserExistsDeleteCalled() {
        Long userId = 1L;

        userService.delete(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

}