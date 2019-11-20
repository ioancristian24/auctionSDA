package com.sda.auction.jwt;

import com.sda.auction.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Component
public class TokenProvider implements InitializingBean {

    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @Value("${jwt.server.secret}")
    private String serverSecret;

    private Key signingKey;

    @Value("${jwt.role.admin.protected}")
    private String adminProtectedPaths;

    @Value("${jwt.role.user.protected}")
    private String userProtectedPaths;

    public String createJwt(User user) {
        return Jwts.builder()
                .claim("email", user.getEmail())
                .claim("roles", user.getRolesAsStrings())
                .claim("firstName", user.getFirstName())
                .signWith(signatureAlgorithm, signingKey).compact();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(serverSecret);
        signingKey = new SecretKeySpec(keyBytes, signatureAlgorithm.getJcaName());
    }

    public boolean validate(String jwt, String requestURL) {
        if (requestURL.compareTo("/api/login") == 0
            || (requestURL.compareTo("/api/register") == 0)){
            return true;
        }
        Optional<Claims> optionalClaims = decodeJwt(jwt);
        if (!optionalClaims.isPresent()){
            return false;
        }
        Claims claims = optionalClaims.get();
        return authorized(requestURL, adminProtectedPaths, "admin", claims)
                && authorized(requestURL, userProtectedPaths, "user", claims);
    }


    private boolean authorized(String requestURL, String protectedPaths, String role, Claims claims) {
        String []roleProtectedPathsArray = protectedPaths.split(",");
        for (String protectedPath : roleProtectedPathsArray){
            if(requestURL.contains(protectedPath)){
                List<String>userRoles = claims.get("roles", ArrayList.class);
                if (userRoles.contains(role)){
                    return true;
                }
                return false;
            }
        }
        return true;
    }

    private Optional<Claims> decodeJwt(String jwt) {
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(jwt)
                    .getBody();
            return Optional.of(claims);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    public String getEmailFrom(String jwt) {
        return Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJwt(jwt)
                .getBody().get("email", String.class);
    }
}