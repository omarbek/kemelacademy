package kz.academy.kemelacademy.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kz.academy.kemelacademy.SpringApplicationContext;
import kz.academy.kemelacademy.services.IUserService;
import kz.academy.kemelacademy.shared.dto.UserDto;
import kz.academy.kemelacademy.ui.model.request.UserLoginRequestModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-21
 * @project kemelacademy
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
    private final AuthenticationManager authenticationManager;
    
    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            UserLoginRequestModel creds = new ObjectMapper().readValue(request.getInputStream(),
                    UserLoginRequestModel.class);
            
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(),
                    creds.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        middle(response, authResult);
//        hard(response, authResult);
    }
    
    private void hard(HttpServletResponse response, Authentication authResult) throws IOException {
        Map<String, Object> claims = new HashMap<>();
        
//        Claims claims = Jwts.claims().setSubject(((User) authResult.getPrincipal()).getUsername());
//        claims.put("scopes", authResult.getAuthorities().iterator().next());
        String token = Jwts.builder()
                .setSubject(((User) authResult.getPrincipal()).getUsername())
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        String resp = new ObjectMapper().writeValueAsString(Collections.singletonMap("token", token));
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().print(resp);
        response.getWriter().flush();
        response.getWriter().close();
    }
    
    private void middle(HttpServletResponse response, Authentication authResult) {
        String username = ((User) authResult.getPrincipal()).getUsername();
        
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret()).compact();
        
        IUserService userService = (IUserService) SpringApplicationContext.getBean("userServiceImpl");
        UserDto userDto = userService.getUser(username);
        
        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        response.addHeader("UserId", userDto.getUserId());
    }
    
}
