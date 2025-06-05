package ru.itk.spring_secutity.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itk.spring_secutity.jwt.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
