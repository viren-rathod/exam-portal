package org.examportal.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.examportal.Constants.AuthMessages;
import org.examportal.Exceptions.ExamAPIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    //Generating JWT Token
    public String generateToken(Authentication authentication) {
        String username;
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            if (principal instanceof org.examportal.Models.User) {
                username = ((org.examportal.Models.User) principal).getEmail();
            } else {
                username = ((UserDetails) principal).getUsername(); // Fallback
            }
        } else if (principal instanceof OAuth2User) {
            username = (String) ((OAuth2User) principal).getAttributes().get("email");
        } else {
            username = authentication.getName();
        }


        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();
    }

    // get username from Jwt token
    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // validate Jwt token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new ExamAPIException(HttpStatus.UNAUTHORIZED, AuthMessages.INVALID_TOKEN);
        } catch (ExpiredJwtException ex) {
            throw new ExamAPIException(HttpStatus.UNAUTHORIZED, AuthMessages.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException ex) {
            throw new ExamAPIException(HttpStatus.BAD_REQUEST, AuthMessages.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException ex) {
            throw new ExamAPIException(HttpStatus.BAD_REQUEST, AuthMessages.EMPTY_TOKEN);
        }
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
