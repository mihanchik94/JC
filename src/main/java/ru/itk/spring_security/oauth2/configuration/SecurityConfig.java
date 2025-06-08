package ru.itk.spring_security.oauth2.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import ru.itk.spring_security.oauth2.security.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement(sessionManagementConfigurer ->
                                sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/", "/login")
                                .permitAll()
                                .anyRequest().authenticated())
                .oauth2Login(
                        oauth2LoginConfigurer -> oauth2LoginConfigurer
                                .redirectionEndpoint(redirectionEndpointConfig -> redirectionEndpointConfig
                                        .baseUri("/login/oauth2/code/*"))
                                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                        .userService(customOAuth2UserService))
                                .defaultSuccessUrl("/profile", true)
                                .successHandler(authenticationSuccessHandler()))
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutUrl("/logout")
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler(logoutSuccessHandler()))
                .build();

    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            log.info("User {} has successfully authenticated", authentication.getName());
            response.sendRedirect("/profile");
        };
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return ((request, response, authentication) -> {
            if (authentication != null) {
                log.info("Logout of {} is in process", authentication.getName());
            } else {
                log.warn("Logout has already processed");
            }
            response.sendRedirect("/login");
        });
    }
}
