package com.example.hython.domain.member.utils;

import com.example.hython.common.exception.BaseException;
import com.example.hython.common.response.BaseResponseStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTUtils {

    @Value("${token.secret}")
    private String SECRET_KEY;
    @Value("${token.expiration}")
    private Long EXPIRATION_TIME;

    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Long memberId) {
        Date date = new Date();
        return Jwts.builder()
                .claim("memberId", memberId) // 회원 ID
                .setIssuedAt(date) // 발급 시간
                .setExpiration(new Date(date.getTime() + EXPIRATION_TIME)) // 만료 시간
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public Long getMemberIdByToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("memberId", Long.class);
    }

    public String getToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        String token = request.getHeader("Authorization");

        if (token == null) {
            throw new BaseException(BaseResponseStatus.TOKEN_NOT_EXIST);
        }

        // "Bearer "가 포함되어 있는 경우 제거
        if (token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 이후의 토큰 부분만 반환
        }

        return token;
    }

    // 토큰 유효성 검사 -> 유효기간 만료 여부 확인
    public boolean isTokenExpired(String token) {
        return validateToken(token, true) == BaseResponseStatus.EXPIRED_JWT;
    }

    // 토큰 유효성 검사 -> 외부 호출 용
    public boolean validateToken(String token) {
        return validateToken(token, false) == null;
    }

    // 토큰 유효성 검사
    private BaseResponseStatus validateToken(String token, boolean checkExpirationOnly) {
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build().parseClaimsJws(token);
            return null;
        } catch (ExpiredJwtException e) {
            // 만료된 토큰
            if (checkExpirationOnly) return BaseResponseStatus.EXPIRED_JWT;
            throw new BaseException(BaseResponseStatus.EXPIRED_JWT);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new BaseException(BaseResponseStatus._INVALID_JWT);
        } catch (UnsupportedJwtException e) {
            throw new BaseException(BaseResponseStatus._UNSUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            throw new BaseException(BaseResponseStatus._EMPTY_JWT);
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
