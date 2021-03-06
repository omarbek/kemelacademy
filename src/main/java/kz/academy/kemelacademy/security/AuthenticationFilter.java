package kz.academy.kemelacademy.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kz.academy.kemelacademy.services.IUserService;
import kz.academy.kemelacademy.ui.dto.RoleDto;
import kz.academy.kemelacademy.ui.dto.UserDto;
import kz.academy.kemelacademy.ui.model.request.UserLoginRequestModel;
import kz.academy.kemelacademy.utils.SystemParameterUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
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
    private final IUserService userService;
    
    private Long expirationTime;
    
    AuthenticationFilter(AuthenticationManager authenticationManager, IUserService userService,
                         SystemParameterUtils systemParameterUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        
        expirationTime = systemParameterUtils.getExpirationTime();
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {//login-1
        try {
            UserLoginRequestModel creds = new ObjectMapper().readValue(request.getInputStream(),
                    UserLoginRequestModel.class);
            
            UserDto userDto = userService.getUser(creds.getEmail());
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(),
                    creds.getPassword(), getAuthority(userDto)));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    
    private Set<GrantedAuthority> getAuthority(UserDto userDto) {
        Set<GrantedAuthority> set = new HashSet<>();
        for (RoleDto roleDto: userDto.getRoles()) {
            set.add((GrantedAuthority) roleDto::getName);
        }
        return set;
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {
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
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
        response.addHeader(SecurityConstants.HEADER_STRING, /*SecurityConstants.TOKEN_PREFIX +*/ token);
        String resp = new ObjectMapper().writeValueAsString(Collections.singletonMap("token", token));
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().print(resp);
        response.getWriter().flush();
        response.getWriter().close();
    }
    
    private void middle(HttpServletResponse response, Authentication authResult) throws IOException {//login-3
        String username = ((User) authResult.getPrincipal()).getUsername();
        
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
        String token = Jwts.builder().setSubject(username)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret()).compact();
        
        Date refreshTokenExpirationDate = new Date(System.currentTimeMillis() + (expirationTime * 4));
        String refreshToken = Jwts.builder().setSubject(username)
                .setExpiration(refreshTokenExpirationDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getRefreshTokenSecret()).compact();
        
        
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("refreshToken", refreshToken);
        map.put("expirationDate", expirationDate);
        map.put("refreshTokenExpirationDate", refreshTokenExpirationDate);
        String resp = new ObjectMapper().writeValueAsString(map);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().print(resp);
        response.getWriter().flush();
        //        response.getWriter().close();
    }
    
}
