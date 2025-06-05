package ru.itk.spring_secutity.jwt.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.itk.spring_secutity.jwt.model.Role;
import ru.itk.spring_secutity.jwt.model.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class JwtUserDetailsFactory {

    private JwtUserDetailsFactory() {
    }

    public static JwtUserDetails create(User user) {
        return JwtUserDetails.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .isAccountNonLocked(user.isAccountNonLocked())
                .authorities(mapToGrantedAuthorities(user.getRoles()))
                .build();
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }
}
