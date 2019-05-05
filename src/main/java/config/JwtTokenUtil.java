package config;


import Interceptor.SimpleInterceptor;
import domain.authentication.Privilege;
import domain.authentication.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
/**
 * @author Alex
 * jwtToken class voor token methodes
 **/
@Interceptors(SimpleInterceptor.class)
public class JwtTokenUtil implements Serializable {
    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
    public static final String SIGNING_KEY = "Alex";
    //Email uit token krijgen
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //ExperationDate uit token halen
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    //claims ophalen met Resolver
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //claims ophalen uit token
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
    //boolean voor role check in token
    public boolean containsScopeInToken(String token,String scopeName) {
        String scope = getAllClaimsFromToken(token).get("scopes").toString();
        return scope.contains(scopeName);

    }
    //boolean op token niet meer geldig op tijd
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(User user) {
        return doGenerateToken(user.getEmail(),user.getPrivileges());
    }
    //Token maken
    private String doGenerateToken(String subject, List<Privilege> privileges) {

        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("scopes", Arrays.asList(privileges));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("http://alexanderkir.nl")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }
    //Token validatie
    public Boolean validateToken(String token) {
        final String username = getUsernameFromToken(token);
        return (
                username != null
                    && !isTokenExpired(token));
    }

}