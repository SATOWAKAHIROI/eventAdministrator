package com.example.backend.security;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.example.backend.entity.User.RoleType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${jwt.secret}")
    private String secretKey;
    
    @Value("${jwt.expiration:86400000}") // デフォルト: 24時間
    private long validityInMilliseconds;

    /**
     * JWTトークンを検証するメソッド
     * @param token
     * @return
     */
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                .setSigningKey(getSiginingKey())
                .build()
                .parseClaimsJws(token);
            logger.debug("JWTトークンの検証に成功しました。");
            return true;
        }catch(SecurityException | MalformedJwtException e){
            logger.error("無効なJWT署名です", e.getMessage());
        }catch (ExpiredJwtException e) {
            logger.error("期限切れのJWTトークンです: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("サポートされていないJWTトークンです: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWTトークンが空です: {}", e.getMessage());
        }

        return false;
    }

    /**
     * トークンからユーザーIDを取得するメソッド
     * @param token
     * @return
     */
    public Long getUserIdFromToken(String token){
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(getSiginingKey())
                            .build()
                            .parseClaimsJws(token)
                            .getBody();

        return claims.get("userId", Long.class);
    }

    /**
     * トークンからメールアドレスを取得するメソッド
     * @param token
     * @return
     */
    public String getEmailFromToken(String token){
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(getSiginingKey())
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
        
        return claims.getSubject();
    }

    /**
     * トークンからユーザーの権限を取得するメソッド
     * @param token
     * @return
     */
    public RoleType getUserRoleFromToken(String token){
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(getSiginingKey())
                            .build()
                            .parseClaimsJws(token)
                            .getBody();

        String roleString = claims.get("userRole", String.class);
        return RoleType.valueOf(roleString);
    }

    /**
     * JWTトークンから認証情報を作成するメソッド
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token){
        String email = getEmailFromToken(token);
        Long userId = getUserIdFromToken(token);
        RoleType userRole = getUserRoleFromToken(token);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole));

        UserPrincipal principal = new UserPrincipal(userId, email);

        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }

    /**
     * JWTトークンを生成するメソッド
     * @param userId
     * @param email
     * @param userRole
     * @return
     */
    public String generateToken(Long userId, String email, RoleType userRole){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                    .setSubject(email)
                    .claim("userId", userId)
                    .claim("userRole", userRole)
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(getSiginingKey(), SignatureAlgorithm.HS512)
                    .compact();
    }

    /**
     * 秘密鍵を取得するメソッド
     * @return
     */
    private SecretKey getSiginingKey(){
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 認証ユーザー情報を保持するクラス
     */
    public static class UserPrincipal {
        private final Long userId;
        private final String email;

        public UserPrincipal(Long userId, String email){
            this.userId = userId;
            this.email = email;
        }

        public Long getUserId() {
            return userId;
        }

        public String getEmail() {
            return email;
        }
    }
}
