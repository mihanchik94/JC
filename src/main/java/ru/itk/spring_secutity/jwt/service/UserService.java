package ru.itk.spring_secutity.jwt.service;

import ru.itk.spring_secutity.jwt.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();
    UserDto findById(Long id);
    UserDto update(Long id, UserDto userDto);
    void delete(Long id);
    void unblockUser(Long id);
}
