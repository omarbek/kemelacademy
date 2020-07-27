package kz.academy.kemelacademy.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-27
 * @project kemelacademy
 */
@RestController
@RequestMapping("/user")
public class LoginController {
    
    @GetMapping
    public Principal getUser(Principal user) {
        return user;
    }
    
}
