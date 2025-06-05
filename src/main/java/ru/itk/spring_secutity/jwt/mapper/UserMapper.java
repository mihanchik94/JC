package ru.itk.spring_secutity.jwt.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itk.spring_secutity.jwt.dto.UserDto;
import ru.itk.spring_secutity.jwt.model.Role;
import ru.itk.spring_secutity.jwt.model.RoleName;
import ru.itk.spring_secutity.jwt.model.User;
import ru.itk.spring_secutity.jwt.service.RoleService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true))
public abstract class UserMapper {
    @Autowired
    private RoleService roleService;

    @Mapping(target = "roles", expression = "java(getUserRoleSet())")
    public abstract User fromUserRegistrationDtoToUserWithRoleUser(UserDto userDto);

    public abstract UserDto fromUserToUserDto(User user);

    public abstract List<UserDto> fromUserListToUserDtoList(List<User> users);

    protected Set<Role> getUserRoleSet() {
        Role role = roleService.findByName(RoleName.ROLE_USER);
        Set<Role> result = new HashSet<>();
        result.add(role);
        return result;
    }
}
