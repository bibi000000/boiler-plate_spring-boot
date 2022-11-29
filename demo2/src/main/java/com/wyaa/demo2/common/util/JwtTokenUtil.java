package com.wyaa.demo2.common.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenUtil {
    private final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
    private final RedisUtil redisUtil;
    private final Key key;
    private final Integer accessTokenExpiration;
    private final Integer refreshTokenExpiration;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String ISSUER = "wyaa";
    public static final String AUTHORITIES_KEY = "auth";

    @Autowired
    public JwtTokenUtil(@Value("${jwt.secret}") String secretKey,
                        @Value("${jwt.access-token-expiration}") Integer accessTokenExpiration,
                        @Value("${jwt.refresh-token-expiration}") Integer refreshTokenExpiration,
                        RedisUtil redisUtil) {
        String encodingSecretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        byte[] decoded = Base64.getDecoder().decode(encodingSecretKey.getBytes(StandardCharsets.UTF_8));
        this.key = Keys.hmacShaKeyFor(decoded);
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.redisUtil = redisUtil;
    }

    public String createAccessToken(int userAccountSeq) {
        Date expires = JwtTokenUtil.getTokenExpiration(accessTokenExpiration);
        return Jwts.builder()
                .setSubject("Access Token")
                .claim("userAccountSeq", userAccountSeq)
                .setExpiration(expires)
                .setIssuer(ISSUER)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createRefreshToken() {
        Date expires = JwtTokenUtil.getTokenExpiration(refreshTokenExpiration);
        return Jwts.builder()
                .setSubject("Refresh Token")
                .setExpiration(expires)
                .setIssuer(ISSUER)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        String tokenDelPrefix = token.replace(TOKEN_PREFIX, "");
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(tokenDelPrefix)
                .getBody();
    }

    public int getUserAccountSeq(String token) {
        return extractAllClaims(token).get("userAccountSeq", Integer.class);
    }

    public static Date getTokenExpiration(Integer expirationTime) {
        Date now = new Date();
        return new Date(now.getTime() + expirationTime);
    }

    public Long getTokenExpirationAsLong(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        Long now = new Date().getTime();
        return (expiration.getTime()-now);
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token) {
        String tokenDelPrefix = token.replace(TOKEN_PREFIX, "");
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(tokenDelPrefix);
            if (redisUtil.hasKeyBlackList(token)) {
                return false;
            }
            return true;
        } catch (SignatureException ex) {
            log.error("validateToken - Not valid JWT signature.");
            throw new SignatureException("Not valid JWT signature.");
        } catch (MalformedJwtException ex) {
            log.error("validateToken - Malformed JWT token.");
            throw new MalformedJwtException("Malformed JWT token.");
        } catch (ExpiredJwtException ex) {
            log.error("validateToken - Expired JWT token.");
            throw new NullPointerException("Expired JWT token.");
        } catch (UnsupportedJwtException ex) {
            log.error("validateToken - Unsupported JWT token.");
            throw new UnsupportedJwtException("Unsupported JWT token.");
        } catch (IllegalArgumentException ex) {
            log.error("validateToken - Empty token.");
            throw new IllegalArgumentException("Empty token.");
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}
