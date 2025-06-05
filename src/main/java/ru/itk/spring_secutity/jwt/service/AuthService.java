package ru.itk.spring_secutity.jwt.service;

import ru.itk.spring_secutity.jwt.dto.AuthResponseDto;
import ru.itk.spring_secutity.jwt.dto.RefreshTokenRequestDto;
import ru.itk.spring_secutity.jwt.dto.UserDto;

public interface AuthService {
    UserDto register(UserDto userDto);
    AuthResponseDto login(UserDto userDto);
    AuthResponseDto refreshToken(RefreshTokenRequestDto request);


}
