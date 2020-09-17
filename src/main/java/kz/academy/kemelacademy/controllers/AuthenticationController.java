package kz.academy.kemelacademy.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import kz.academy.kemelacademy.security.SecurityConstants;
import kz.academy.kemelacademy.ui.model.request.LoginRequestModel;
import kz.academy.kemelacademy.ui.model.request.RefreshTokenModel;
import kz.academy.kemelacademy.utils.SystemParameterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-24
 * @project kemelacademy
 */
@RestController
public class AuthenticationController {
    
    @Autowired
    private SystemParameterUtils systemParameterUtils;
    
    @ApiOperation("User Login")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Response Headers",
                    responseHeaders = {
                            @ResponseHeader(name = "authorization",
                                    description = "Bearer <JWT value here>",
                                    response = String.class),
                            @ResponseHeader(name = "userId",
                                    description = "<Public User Id value here>",
                                    response = String.class)
                    }
            )
    })
    @PostMapping("/users/login")
    public void theFakeLogin(@RequestBody LoginRequestModel loginRequestModel) {
        throw new IllegalStateException("This method should not be called. " +
                "This method is implemented by Spring Security");
    }
    
    @PostMapping("/refresh-token")
    public Map refreshToken(@Valid @RequestBody RefreshTokenModel refreshTokenModel) {
        try {
            String user = Jwts.parser().setSigningKey(SecurityConstants.getRefreshTokenSecret())
                    .parseClaimsJws(refreshTokenModel.getRefreshToken())
                    .getBody()
                    .getSubject();
            
            Date expirationDate = new Date(System.currentTimeMillis() + systemParameterUtils.getExpirationTime());
            String token = Jwts.builder().setSubject(user)
                    .setExpiration(expirationDate)
                    .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret()).compact();
            
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("expirationDate", expirationDate);
            return map;
        } catch (Exception e) {
            throw new IllegalStateException("Invalid refresh token");
        }
    }
    
}
