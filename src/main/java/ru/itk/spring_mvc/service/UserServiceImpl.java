package ru.itk.spring_mvc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itk.spring_mvc.dto.UserDto;
import ru.itk.spring_mvc.mapper.UserMapper;
import ru.itk.spring_mvc.model.User;
import ru.itk.spring_mvc.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> findAll() {
        return userMapper.fromUserListToUserDtoList(userRepository.findAll());
    }

    @Override
    public UserDto findById(Long userId) {
        return userMapper.fromUserToUserDto(findUserById(userId));
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = userMapper.fromUserDtoToUser(userDto);
        return userMapper.fromUserToUserDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserDto update(Long id, UserDto userDto) {
        User existingUser = findUserById(id);
        existingUser.setName(userDto.getName());
        existingUser.setSurname(userDto.getSurname());
        existingUser.setEmail(userDto.getEmail());
        return userMapper.fromUserToUserDto(userRepository.save(existingUser));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with id = %d not found", id)));
    }
}
