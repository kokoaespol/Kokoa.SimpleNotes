package io.github.alicarpio.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static final long SIX_HOURS_IN_MILLISECONDS = 21600000;
    private static final long FIFTEEN_MINUTES_IN_MILLISECONDS = 15 * 60 * 1000;
    private static final long ACCESS_TOKEN_EXPIRATION = FIFTEEN_MINUTES_IN_MILLISECONDS;
    private static final long REFRESH_TOKEN_EXPIRATION = SIX_HOURS_IN_MILLISECONDS;
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateAccessToken(String subject) {
        return generateToken(subject, ACCESS_TOKEN_EXPIRATION);
    }

    public static String generateRefreshToken(String subject) {
        return generateToken(subject, REFRESH_TOKEN_EXPIRATION);
    }

    public static String generateToken(String subject, long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public static String getSubjectFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}