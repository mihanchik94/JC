package ru.itk.spring_secutity.jwt.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itk.spring_secutity.jwt.dto.UserDto;
import ru.itk.spring_secutity.jwt.mapper.UserMapper;
import ru.itk.spring_secutity.jwt.model.User;
import ru.itk.spring_secutity.jwt.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    @Override
    public List<UserDto> findAll() {
        return userMapper.fromUserListToUserDtoList(userRepository.findAll());
    }

    @Override
    public UserDto findById(Long id) {
        return userMapper.fromUserToUserDto(findUserById(id));
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        User user = findUserById(id);
        user.setUsername(userDto.getUsername());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setRoles(userDto.getRoles());
        return userMapper.fromUserToUserDto(userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void unblockUser(Long id) {
        User user = findUserById(id);
        user.setAccountNonLocked(true);
        userRepository.save(user);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id: %d not found", id)));
    }
}
