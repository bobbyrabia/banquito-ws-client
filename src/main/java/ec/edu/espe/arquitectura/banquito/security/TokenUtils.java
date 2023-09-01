package ec.edu.espe.arquitectura.banquito.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.*;

public class TokenUtils {
    private final static String ACCESS_TOKEN_SECRET="3N1ZXIiOiJJc3N1ZXJzIiwiVXNlcm5hbWUiOiJKYXZhSW5Vc2UiLCJleHAi";
    private final static Long ACCESS_TOKEN_VALIDITY_SECONDS=2_592_000L;
    public static String createToken(String username, String email){
        long expirationTime=ACCESS_TOKEN_VALIDITY_SECONDS+1_000;
        Date expirationDate=new Date(System.currentTimeMillis()+expirationTime);
        Map<String, Object>extra=new HashMap<>();
        extra.put("nombre",username);
        return Jwts.builder().setSubject(email).setExpiration(expirationDate).addClaims(extra).signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes())).compact();
    }
    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
        try{
            Claims claims = Jwts.parserBuilder().setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build().parseClaimsJws(token).getBody();
            String email=claims.getSubject();
            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        }catch (JwtException e){
            return null;
        }
    }
}
