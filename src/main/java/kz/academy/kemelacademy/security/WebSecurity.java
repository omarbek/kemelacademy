package kz.academy.kemelacademy.security;

import kz.academy.kemelacademy.services.IUserService;
import kz.academy.kemelacademy.utils.SystemParameterUtils;
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
    private final SystemParameterUtils systemParameterUtils;
    
    public WebSecurity(IUserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder,
                       SystemParameterUtils systemParameterUtils) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.systemParameterUtils = systemParameterUtils;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //        http.requiresChannel().anyRequest().requiresSecure();
        http.cors();
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
                .antMatchers(HttpMethod.GET, SecurityConstants.COURSES_URL).permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.FILE_TYPES_URL).permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.LANGUAGES_URL).permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.LESSON_TYPES_URL).permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.LEVELS_URL).permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.ROLES_URL).permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.TEST_STATUSES_URL).permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.COURSE_STATUSES_URL).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.REFRESH_TOKEN_URL).permitAll()
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
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager(), userDetailsService,
                systemParameterUtils);
        filter.setFilterProcessesUrl("/users/login");
        return filter;
    }
    
}
