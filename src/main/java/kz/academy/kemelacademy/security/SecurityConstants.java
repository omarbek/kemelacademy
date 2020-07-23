package kz.academy.kemelacademy.security;

import kz.academy.kemelacademy.SpringApplicationContext;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-21
 * @project kemelacademy
 */
class SecurityConstants {
    
    static final String SECRET = "SecretKeyToGenJWTs";
    static final long EXPIRATION_TIME = 864000000; //10 days
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final String SIGN_UP_URL = "/users";
    
    static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
    
}
