package lk.ijse.NoteCollecter_V2.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lk.ijse.NoteCollecter_V2.service.JWTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
public class JWTServiceImpl implements JWTService {
    @Value("${spring.jwtKey}")
    String jwtKey;
    @Override
    public String generateToken(UserDetails user) {
        return genToken(new HashMap<>(),user);
    }

    @Override
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public String refreshToken(UserDetails userDetails) {
        return refreshToken(new HashMap<>(),userDetails);
    }
    private <T>T extractClaims(String token, Function<Claims, T> claimsResolv) {
        final Claims claims = getClaims(token);
        return claimsResolv.apply(claims);
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }
    private Key getSignKey() {
        byte[] decodedKey = Base64.getDecoder().decode(jwtKey);
        return Keys.hmacShaKeyFor(decodedKey);
    }
    private String genToken(Map<String, Object> genClaims, UserDetails userDetails) {
        genClaims.put("role",userDetails.getAuthorities());
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * 600);
        return Jwts.builder().setClaims(genClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();

    }
    private boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }
    private Date getExpirationDate(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
    private String refreshToken(Map<String, Object> genClaimsRefresh, UserDetails userDetails) {
        genClaimsRefresh.put("role",userDetails.getAuthorities());
        Date now = new Date();
        Date refreshExpire = new Date(now.getTime() + 1000 * 600 * 600);

        return Jwts.builder().setClaims(genClaimsRefresh)
                .setSubject(userDetails.getUsername())
                .setExpiration(refreshExpire)
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }
}
