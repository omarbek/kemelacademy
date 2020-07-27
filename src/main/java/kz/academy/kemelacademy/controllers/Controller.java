package kz.academy.kemelacademy.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-27
 * @project kemelacademy
 */
@RestController
public class Controller {
    
    @RequestMapping(value = "/user")
    public Principal user(Principal principal) {
        return principal;
    }
    
}
