package ru.itk.spring_secutity.jwt.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itk.spring_secutity.jwt.dto.AuthResponseDto;
import ru.itk.spring_secutity.jwt.dto.RefreshTokenRequestDto;
import ru.itk.spring_secutity.jwt.dto.UserDto;
import ru.itk.spring_secutity.jwt.mapper.UserMapper;
import ru.itk.spring_secutity.jwt.model.User;
import ru.itk.spring_secutity.jwt.repository.UserRepository;
import ru.itk.spring_secutity.jwt.security.JwtTokenProvider;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private static final int MAX_ATTEMPTS = 5;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserDto register(UserDto userDto) {
        User user = userMapper.fromUserRegistrationDtoToUserWithRoleUser(userDto);
        user.setPassword(encoder.encode(user.getPassword()));
        return userMapper.fromUserToUserDto(userRepository.save(user));
    }

    @Override
    public AuthResponseDto login(UserDto userDto) {
        User user = findUserByName(userDto.getUsername());
        isUserLocked(user);
        if (encoder.matches(userDto.getPassword(), user.getPassword())) {
            saveUserIfFailedAttemptsIsNotZero(user);
            String accessToken = jwtTokenProvider.createAccessToken(user);
            String refreshToken = jwtTokenProvider.createRefreshToken(user);
            return new AuthResponseDto(accessToken, refreshToken);
        } else {
            user.setFailedAttempts(user.getFailedAttempts() + 1);
            if (user.getFailedAttempts() >= MAX_ATTEMPTS) {
                blockUser(user);
                throw new IllegalStateException("User is locked! Contact the administrator");
            } else {
                userRepository.save(user);
                throw new IllegalArgumentException(
                        "Invalid password. " + (MAX_ATTEMPTS - user.getFailedAttempts()) + " attempts left");
            }
        }
    }

    @Override
    public AuthResponseDto refreshToken(RefreshTokenRequestDto request) {
        String refreshToken = request.getRefreshToken();
        String username = jwtTokenProvider.getUserNameFromToken(refreshToken);
        User user = findUserByName(username);
        String tokenType = jwtTokenProvider.getTokenTypeFromToken(refreshToken);
        if ("refresh".equals(tokenType) && jwtTokenProvider.validateToken(refreshToken)) {
            String accessToken = jwtTokenProvider.createAccessToken(user);
            return new AuthResponseDto(accessToken, refreshToken);
        } else {
            throw new IllegalArgumentException("Invalid refresh token");
        }
    }

    private User findUserByName(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with email: %s not found", username)));
    }

    private void isUserLocked(User user) {
        if (!user.isAccountNonLocked()) {
            throw new IllegalStateException("User is locked! Contact the administrator");
        }
    }

    public void saveUserIfFailedAttemptsIsNotZero(User user) {
        if (user.getFailedAttempts() > 0) {
            user.setFailedAttempts(0);
            userRepository.save(user);
        }
    }

    private void blockUser(User user) {
        user.setAccountNonLocked(false);
        userRepository.save(user);
        log.info("Account locked - Username: {}", user.getUsername());
    }

}
