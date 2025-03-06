package com.abhinav.config;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {

    static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public static String generateToken(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorites(authorities);
        String jwt = Jwts.builder()
             .setIssuedAt(new Date())
              .setExpiration(new Date(new Date().getTime() + 8600000))
              .claim("email",auth.getName())
              .claim("authorities", roles)
              .signWith(key)
              .compact();
               return jwt;
    }

    private static String populateAuthorites(Collection<? extends GrantedAuthority> authorities) {
        Set<String> map = new HashSet<>();
        for(GrantedAuthority authority : authorities){
            map.add(authority.getAuthority());
        }
        return String.join(",",map);
    }

    public static String getEmailFromToken(String jwt){
        jwt = jwt.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        return claims.get("email").toString();
    }

}
