package ru.itk.spring_security.oauth2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itk.spring_security.oauth2.model.User;
import ru.itk.spring_security.oauth2.security.CustomOAuth2User;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> getUserProfile(@AuthenticationPrincipal OAuth2User oAuth2User) {
        User user = ((CustomOAuth2User) oAuth2User).getUser();
        return ResponseEntity.ok(user);
    }
}
