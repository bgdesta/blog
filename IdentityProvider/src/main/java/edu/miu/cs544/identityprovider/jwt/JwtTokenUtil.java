package edu.miu.cs544.identityprovider.jwt;

import edu.miu.cs544.identityprovider.domain.UserSecurity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;


    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expirationMs}")
    private int JwtExpirationMs;

    public String generateJwtToken(UserSecurity userSecurity) {
        return generateTokenFromUserName(userSecurity.getUsername());
    }
    public String generateJwtToken(Authentication authentication) {
        UserSecurity userPrincipal = (UserSecurity) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

    }
    public String generateTokenFromUserName(String userName) {
        return Jwts.builder().setSubject(userName).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + JwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody().getSubject();
    }
    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
           // logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            //logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            //logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            //logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            //logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

}
