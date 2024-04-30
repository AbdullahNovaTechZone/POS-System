package com.novatechzone.pos.domain.security.util;

import com.novatechzone.pos.domain.security.entity.Owner;
import com.novatechzone.pos.exception.ApplicationCustomException;
import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.accessTokenLife}")
    private Long accessTokenLife;
    @Value("${jwt.refreshTokenLife}")
    private Long refreshTokenLife;
    @Value("${jwt.issuer}")
    private String issuer;

    private String generateToken(Map<String, Object> claims, Long expiration, String subject) {

        Signer signer = HMACSigner.newSHA256Signer(secret);
        JWT jwt = new JWT().setIssuer(issuer)
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .setSubject(subject)
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(expiration));

        claims.forEach(jwt::addClaim);

        return JWT.getEncoder().encode(jwt, signer);
    }

    private Map<String, Object> getClaimFromToken(String token) {
        Verifier verifier = HMACVerifier.newVerifier(secret);
        JWT jwt = JWT.getDecoder().decode(token, verifier);
        return jwt.getAllClaims();
    }

    public boolean validateToken(String token) {
        Verifier verifier = HMACVerifier.newVerifier(secret);
        try {
            JWT jwt = JWT.getDecoder().decode(token, verifier);
            return !jwt.isExpired();
        } catch (Exception e) {
            throw new ApplicationCustomException(HttpStatus.UNAUTHORIZED, "INVALID_TOKEN", "Invalid Token");
        }
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token).get(CLAIM_KEY_USERNAME).toString();
    }

    public String generateAccessToken(Owner owner) {

        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put("id", owner.getId());

        return generateToken(claims, accessTokenLife, owner.getUsername());
    }

    public String generateRefreshToken(Owner owner) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put("id", owner.getId());

        return generateToken(claims, refreshTokenLife, owner.getUsername());
    }
}
