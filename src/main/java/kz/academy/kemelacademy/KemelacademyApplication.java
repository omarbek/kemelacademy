package kz.academy.kemelacademy;

import kz.academy.kemelacademy.security.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class KemelacademyApplication extends SpringBootServletInitializer {
    
    public static void main(String[] args) {
        SpringApplication.run(KemelacademyApplication.class, args);
    }
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SpringApplicationContext springApplicationContext() {
        return new SpringApplicationContext();
    }
    
    @Bean(name = "AppProperties")
    public AppProperties getAppProperties() {
        return new AppProperties();
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(KemelacademyApplication.class);
    }
    
}
