package kz.academy.kemelacademy.security;

import io.jsonwebtoken.Jwts;
import kz.academy.kemelacademy.services.IUserService;
import kz.academy.kemelacademy.ui.dto.UserDto;
import kz.academy.kemelacademy.ui.entity.RoleEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-21
 * @project kemelacademy
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {
    
    private IUserService userService;
    
    AuthorizationFilter(AuthenticationManager authenticationManager, IUserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(SecurityConstants.HEADER_STRING);
        
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        
        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }
    
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        
        if (token != null) {
            token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
            String user = Jwts.parser().setSigningKey(SecurityConstants.getTokenSecret())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            
            if (user != null) {
                UserDto userDto = userService.getUser(user);
                return new UsernamePasswordAuthenticationToken(user, null, getAuthority(userDto));
            }
            return null;
        }
        return null;
    }
    
    private Set<GrantedAuthority> getAuthority(UserDto userDto) {
        Set<GrantedAuthority> set = new HashSet<>();
        for (RoleEntity roleEntity: userDto.getRoles()) {
            set.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return roleEntity.getName();
                }
            });
        }
        return set;
    }
    
}
