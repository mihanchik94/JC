package ru.itk.spring_security.oauth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itk.spring_security.oauth2.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByProviderAndProviderId(String provider, String providerId);
}
