package com.spring.security_impl.security.jwt;

import com.spring.security_impl.model.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Integer expiration;

    private SecretKey key;

    @PostConstruct
    public void initKey(){
        this.key = Keys.hmacShaKeyFor( secret.getBytes() );
    }

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject( userDetails.getUsername() )
                .expiration( new Date( System.currentTimeMillis() + ( expiration * 1000 ) ))
                .signWith( key )
                .compact();
    }

    public String generateToken(User user){
        return Jwts.builder()
                .subject( user.getUsername() )
                .expiration( new Date( System.currentTimeMillis() + ( expiration * 1000 ) ))
                .signWith( key )
                .compact();
    }

    public String getUsernameFromToken(String token){
        return Jwts.parser().verifyWith( key )
                .build()
                .parseSignedClaims( token )
                .getPayload().getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().verifyWith( key )
                    .build()
                    .parseSignedClaims( token );
            return true;
        }catch (JwtException e){
            log.error("ValidateToken - {}", e.getMessage());
            return false;
        }
    }
}
