// package com.milind.config;

// import javax.crypto.SecretKey;

// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.GrantedAuthority;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.security.Keys;

// public class JwtProvider {
//     private static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

//     public static String generate Token (Authentication auth) { no usages 
//         Collection<? extends GrantedAuthority> authorities auth.getAuthorities(); 
//         String roles=populateAuthorities (authorities); 
//         String jwt= Jwts.builder() 
//                 .setIssuedAt(new Date()) 
//                 .setExpiration (new Date(new Date().getTime()+86400000)) 
//                 .claim(name: "email", auth.getName()) 
//                 .claim(name: "authorities", roles) 
//                 .signWith(key)
//                 .compact(); 
//             return jwt; 
//         }

//         public static String getEmailFromToken(String token) {
//             token = token.substring(7);
//             Claims claims = Jwts.parser()
//                 .verifyWith(key)
//                 .build()
//                 .parseSignedClaims(token)
//                 .getPayload();
//             String email = String.valueOf(claims.get("email"));
//             return email;
//         }

//         private static String populateAuthorities (Collection<? extends GrantedAuthority> authoriti
//             Set<String> auth=new HashSet<>(); 
//             for (GrantedAuthority ga:authorities){ 
//                 auth.add(ga.getAuthority()); 
//             } 
//             return String.join( ",", auth); 
//         }

// }



package com.milind.config;

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

    private static final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public static String generateToken(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);

        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000)) // 1 day
                .claim("email", auth.getName())
                .claim("authorities", roles)
                .signWith(key)
                .compact();
        return jwt;
    }

    public static String getEmailFromToken(String token) {
        token = token.substring(7); // remove "Bearer " part if present
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return String.valueOf(claims.get("email"));
    }

    private static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auth = new HashSet<>();
        for (GrantedAuthority ga : authorities) {
            auth.add(ga.getAuthority());
        }
        return String.join(",", auth);
    }
}
