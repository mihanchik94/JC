package ru.itk.spring_secutity.jwt.security;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import ru.itk.spring_secutity.jwt.model.Role;
import ru.itk.spring_secutity.jwt.model.User;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String secret;

    @Value("${jwt.access-token-expired}")
    private long validityAccessTokenInMilliseconds;

    @Value("${jwt.refresh-token-expired}")
    private long validityRefreshTokenInMilliseconds;


    public String createAccessToken(User user) {
        return createToken(user, "access",  validityAccessTokenInMilliseconds);
    }

    public String createRefreshToken(User user) {
        return createToken(user, "refresh", validityRefreshTokenInMilliseconds);
    }

    private String createToken(User user, String tokenType, long validityTimeInMilliSeconds) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("roles", getRoleNames(user.getRoles()));
        claims.put("token_type", tokenType);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityTimeInMilliSeconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }


    private List<String> getRoleNames(Set<Role> roles) {
        return roles.stream()
                .map(role -> role.getName().name())
                .toList();
    }

    public String resolveToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                log.info("IN validateToken - JWT token has expired");
                return false;
            }
            String tokenType = claims.getBody().get("token_type", String.class);
            if (!("access".equals(tokenType) || "refresh".equals(tokenType))) {
                log.error("IN validateToken - Invalid token type");
                return false;
            }
            log.info("IN validateToken - JWT token valid");
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("IN validateToken  - expired or invalid JWT token");
            return false;
        }
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String getTokenTypeFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("token_type", String.class);
    }
}
