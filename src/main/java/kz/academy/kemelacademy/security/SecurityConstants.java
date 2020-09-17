package kz.academy.kemelacademy.security;

import kz.academy.kemelacademy.SpringApplicationContext;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-21
 * @project kemelacademy
 */
public class SecurityConstants {
    
    static final String SECRET = "SecretKeyToGenJWTs";
    public static final long PASSWORD_RESET_EXPIRATION_TIME = 3600000; //1 hour
    //    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    
    static final String SIGN_UP_URL = "/users";
    static final String VERIFICATION_EMAIL_URL = "/users/email-verification";
    static final String PASSWORD_RESET_REQUEST_URL = "/users/password-reset-request";
    static final String PASSWORD_RESET_URL = "/users/password-reset";
    static final String HELLO_URL = "/users/hello-inter";
    
    static final String INDEX = "/index";
    static final String UPLOAD = "/upload";
    static final String UPLOAD_STATUS = "/uploadStatus";
    
    static final String CATEGORIES_URL = "/categories/**";
    static final String CHAPTERS_URL = "/chapters/**";
    static final String COURSES_URL = "/courses/**";
    static final String FILE_TYPES_URL = "/file_types/**";
    static final String LANGUAGES_URL = "/languages/**";
    static final String LESSON_TYPES_URL = "/lesson_types/**";
    static final String LEVELS_URL = "/levels/**";
    static final String ROLES_URL = "/roles/**";
    static final String TEST_STATUSES_URL = "/test_statuses/**";
    static final String COURSE_STATUSES_URL = "/course_statuses/**";
    static final String REFRESH_TOKEN_URL = "/refresh-token";
    
    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
    
    public static String getRefreshTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getRefreshTokenSecret();
    }
    
}
