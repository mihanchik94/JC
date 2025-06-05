package ru.itk.spring_secutity.jwt.service;

import ru.itk.spring_secutity.jwt.model.Role;
import ru.itk.spring_secutity.jwt.model.RoleName;

public interface RoleService {
    Role findByName(RoleName name);
}
