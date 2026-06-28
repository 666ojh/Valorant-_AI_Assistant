package com.valorantassistant.common.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final SecurityProperties securityProperties;

    public JwtTokenProvider(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public String generateToken(Long userId, String username, String roleCode) {
        Instant now = Instant.now();
        Instant expiresAt = now.plus(securityProperties.getJwtExpirationMinutes(), ChronoUnit.MINUTES);
        return Jwts.builder()
            .subject(username)
            .claim("uid", userId)
            .claim("role", roleCode)
            .issuedAt(Date.from(now))
            .expiration(Date.from(expiresAt))
            .signWith(getSigningKey())
            .compact();
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    private SecretKey getSigningKey() {
        String secret = securityProperties.getJwtSecret();
        byte[] keyBytes = isBase64(secret)
            ? Decoders.BASE64.decode(secret)
            : secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private boolean isBase64(String value) {
        return value.matches("^[A-Za-z0-9+/=]+$") && value.length() % 4 == 0;
    }
}
