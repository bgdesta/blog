package edu.miu.cs544.identityprovider.jwt;

import edu.miu.cs544.identityprovider.domain.UserSecurity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;


    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expirationMs}")
    private int JwtExpirationMs;

    private Key key;

    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        System.out.println(key.getEncoded());
    }

    public String generateJwtToken(UserSecurity userSecurity) {
        return generateTokenFromUserName(userSecurity.getUsername());
    }
    public String generateJwtToken(HashMap<String, Object> claims) {
        System.out.println(claims.get("userName"));
        return Jwts.builder()
                .setSubject((String) claims.get("userName"))
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JwtExpirationMs))
                .signWith(key)
                .compact();

    }
    public String generateTokenFromUserName(String userName) {
        return Jwts.builder().setSubject(userName).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + JwtExpirationMs))
                .signWith(key)
                .compact();
    }
    public String getUserNameFromJwtToken(String token) {
        return (String)Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("userName");
//        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }
    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
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
