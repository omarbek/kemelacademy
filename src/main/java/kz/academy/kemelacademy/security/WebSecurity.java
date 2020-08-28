package kz.academy.kemelacademy.security;

import kz.academy.kemelacademy.services.IUserService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-21
 * @project kemelacademy
 */
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    
    private final IUserService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public WebSecurity(IUserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.VERIFICATION_EMAIL_URL).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_REQUEST_URL).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_URL).permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.INDEX).permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.UPLOAD_STATUS).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.UPLOAD).permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.CATEGORIES_URL).permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.CHAPTERS_URL).permitAll()
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.HELLO_URL).hasRole("MODERATOR")
                .anyRequest().authenticated().and()
                .addFilter(getAuthenticationFilter())
                .addFilter(new AuthorizationFilter(authenticationManager(), userDetailsService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
        
    }
    
    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager(), userDetailsService);
        filter.setFilterProcessesUrl("/users/login");
        return filter;
    }
    
}
