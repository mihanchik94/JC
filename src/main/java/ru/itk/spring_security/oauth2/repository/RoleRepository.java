package ru.itk.spring_security.oauth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itk.spring_security.oauth2.model.Role;
import ru.itk.spring_security.oauth2.model.RoleName;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select r from Role r where r.name = :name")
    Optional<Role> findByName(@Param("name") RoleName name);
}
