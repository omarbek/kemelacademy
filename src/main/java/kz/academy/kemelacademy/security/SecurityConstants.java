package kz.academy.kemelacademy.security;

import kz.academy.kemelacademy.SpringApplicationContext;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-21
 * @project kemelacademy
 */
public class SecurityConstants {
    
    static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864000000; //10 days
    public static final long PASSWORD_RESET_EXPIRATION_TIME = 3600000; //1 hour
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    
    static final String SIGN_UP_URL = "/users";
    static final String VERIFICATION_EMAIL_URL = "/users/email-verification";
    static final String PASSWORD_RESET_REQUEST_URL = "/users/password-reset-request";
    static final String PASSWORD_RESET_URL = "/users/password-reset";
    static final String HELLO_URL = "/users/hello-inter";
    
    static final String INDEX = "/index";
    static final String UPLOAD = "/upload";
    static final String UPLOAD_STATUS = "/uploadStatus";
    
    static final String GET_CATEGORIES_URL = "/categories/**";
    
    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
    
}
