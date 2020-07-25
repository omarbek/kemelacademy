package kz.academy.kemelacademy.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kz.academy.kemelacademy.security.SecurityConstants;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-21
 * @project kemelacademy
 */
@Component
public class GeneratorUtils {
    
    private final Random RANDOM = new SecureRandom();
    
    public static boolean hasTokenExpired(String token) {
        Claims claims = Jwts.parser().setSigningKey(SecurityConstants.getTokenSecret()).parseClaimsJws(token).getBody();
        
        Date tokenExpirationDate = claims.getExpiration();
        Date todayDate = new Date();
        
        return tokenExpirationDate.before(todayDate);
    }
    
    public String generateUserId(int length) {
        return generateRandomString(length);
    }
    
    private String generateRandomString(int length) {
        StringBuilder returnVal = new StringBuilder(length);
        
        for (int i = 0; i < length; i++) {
            String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            returnVal.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        
        return new String(returnVal);
    }
    
    public String generateEmailVerificationToken(String userId) {
        return Jwts.builder().setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512,SecurityConstants.getTokenSecret()).compact();
    }
    
}
