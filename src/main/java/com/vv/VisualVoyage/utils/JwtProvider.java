package com.vv.VisualVoyage.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtProvider {

    private static SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());

    public static String generateToken(Authentication auth){
        return Jwts.builder()
                .setIssuer("Ahmetyalcinkya")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .claim("email", auth.getName())
                .signWith(key)
                .compact();
    }

    public static String getEmailFromJwt(String jwt){
        jwt = jwt.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(jwt).getBody();

        return String.valueOf(claims.get("email"));
    }
}
