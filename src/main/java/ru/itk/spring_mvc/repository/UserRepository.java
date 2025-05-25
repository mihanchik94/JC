package ru.itk.spring_mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itk.spring_mvc.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
