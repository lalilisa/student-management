package com.example.chatapplication.util;



import com.example.chatapplication.common.Constant;
import com.example.chatapplication.domain.Account;
import com.example.chatapplication.dto.response.AccountServiceResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;



@Component
public class JwtTokenUtil implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Value("${jwt.secret}")
    private String backendSecret;

    public String getTokenFromHeader(String header) {
        if (header != null) {
            return header.replace("Bearer ", "");
        }
        return null;
    }

    //retrieve username from jwt token
    public String getTokenTypeFromUntrustedToken(String token) {
        Jwt<Header,Claims> untrusted = Jwts.parser().parseClaimsJwt(token);
        return untrusted.getBody().get("type").toString();
    }

    public String getUserNameFromUntrustedToken(String token) {
        Jwt<Header,Claims> untrusted = Jwts.parser().parseClaimsJwt(token);
        return untrusted.getBody().get("sub").toString();
    }

    public Claims getAllClaim(String token, String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //retrieve username from jwt token
    public String getUsernameFromToken(String token, String secret) {
        return getClaimFromToken(token, secret, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token, String secret) {
        return getClaimFromToken(token, secret, Claims::getExpiration);
    }

    //retrieve expiration date from jwt token
    public Date getIssueDateFromToken(String token, String secret) {
        return getClaimFromToken(token, secret, Claims::getIssuedAt);
    }

    public <T> T getClaimFromToken(String token, String secret, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token, secret);
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token we will need the secret key
    public Claims getAllClaimsFromToken(String token, String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    public Boolean isTokenExpired(String token, String secret) {
        final Date expiration = getExpirationDateFromToken(token, secret);
        return expiration.before(new Date());
    }

    // check if the token trans has expired
    public Boolean isTransTokenExpired(String token, String secret) {
        final Date expiration = getIssueDateFromToken(token, secret);
        return expiration.before(new Date());
    }

    //validate token
    public Boolean validateToken(String token, String secret, UserDetails userDetails) {
        String Token = token.replace("Bearer ", "");
        final String username = getUsernameFromToken(Token, secret);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(Token, secret));
    }


    // generate token for backend
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Constant.JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, backendSecret)
                .compact();
    }
    public String generateToken(AccountServiceResponse account) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId",String.valueOf(account.getUserId()));
        claims.put("userCode",account.getCode());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(account.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Constant.JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, backendSecret)
                .compact();
    }

    // generate token for client
//    public String generateAccountToken(AccountJwtBodyDto accountJwtBodyDto, String secret, Date expire, Date transExpire) {
//        Map<String, Object> claims = new HashMap<>();
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(accountJwtBodyDto.getAccountId())
//                .setIssuedAt(transExpire)
//                .setExpiration(expire)
//                .signWith(SignatureAlgorithm.HS256, secret)
//                .claim("e", accountJwtBodyDto.getExtraToken())
//                .claim("c", accountJwtBodyDto.getChannelToken())
//                .compact();
//    }
}
