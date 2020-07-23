package kz.academy.kemelacademy.security;

import kz.academy.kemelacademy.SpringApplicationContext;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-21
 * @project kemelacademy
 */
public class SecurityConstants {
    
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864000000; //10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";
    
    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
    
}
