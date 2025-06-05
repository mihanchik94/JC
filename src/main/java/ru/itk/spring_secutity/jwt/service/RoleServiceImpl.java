package ru.itk.spring_secutity.jwt.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itk.spring_secutity.jwt.model.Role;
import ru.itk.spring_secutity.jwt.model.RoleName;
import ru.itk.spring_secutity.jwt.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role findByName(RoleName name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Role with name: %s not found", name.name())));
    }
}
