package kz.academy.kemelacademy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-21
 * @project kemelacademy
 */
@Component
public class AppProperties {
    
    @Autowired
    private Environment environment;
    
    String getTokenSecret() {
        return environment.getProperty("tokenSecret");
    }
    
    String getRefreshTokenSecret() {
        return environment.getProperty("refreshTokenSecret");
    }
    
}
