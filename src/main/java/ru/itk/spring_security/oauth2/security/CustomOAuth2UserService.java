package ru.itk.spring_security.oauth2.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ru.itk.spring_security.oauth2.model.Role;
import ru.itk.spring_security.oauth2.model.RoleName;
import ru.itk.spring_security.oauth2.model.User;
import ru.itk.spring_security.oauth2.repository.UserRepository;
import ru.itk.spring_security.oauth2.service.RoleService;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String providerId = String.valueOf(attributes.get(userNameAttributeName));
        Role role = roleService.findByName(RoleName.ROLE_USER);

        User user = userRepository.findByProviderAndProviderId(registrationId, providerId)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setUsername((String) attributes.get("login"));
                    newUser.setProvider(registrationId);
                    newUser.setProviderId(providerId);
                    newUser.getRoles().add(role);
                    return userRepository.save(newUser);
                });
        return new CustomOAuth2User(user, attributes);
    }
}
