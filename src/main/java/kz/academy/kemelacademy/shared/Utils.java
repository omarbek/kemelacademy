package kz.academy.kemelacademy.shared;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-21
 * @project kemelacademy
 */
@Component
public class Utils {
    
    private final Random RANDOM = new SecureRandom();
    
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
    
}
