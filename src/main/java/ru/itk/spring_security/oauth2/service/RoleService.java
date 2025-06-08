package ru.itk.spring_security.oauth2.service;

import ru.itk.spring_security.oauth2.model.Role;
import ru.itk.spring_security.oauth2.model.RoleName;

public interface RoleService {
    Role findByName(RoleName name);
}
