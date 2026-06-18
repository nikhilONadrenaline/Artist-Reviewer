package com.NikhilCreates.practiceProject01.Service;

import com.NikhilCreates.practiceProject01.Enums.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public  class JWTService {
    private  final String SECRET =
            "ThisIsASuperSecretKeyForJwtAuthentication123456";
    private  SecretKey getSignKey()
    {
//        byte[] keyBytes = Decoders.BASE64.decode(SECRET);

        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String email, Role role)
    {
        return Jwts
                .builder()
                .subject(email)
                .claim("role",role.name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60*10))
                .signWith(getSignKey())
                .compact();
    }

    public  String extractUsername(String token)
    {
        return Jwts
                .parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Date extractExpiration(String token)
    {
        return Jwts
                .parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    public boolean isTokenExpired(String token)
    {
        return extractExpiration(token)
                .before(new Date());
    }

    public  boolean validateToken(String token,UserDetails userDetails)
    {
        String username =extractUsername(token);

        return username.equals(userDetails.getUsername())&&!isTokenExpired(token);
    }

    public Role extractRole(String token)
    {
        String role = Jwts
                .parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);

        return Role.valueOf(role);
    }
}
