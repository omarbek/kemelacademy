package kz.academy.kemelacademy.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-27
 * @project kemelacademy
 */
@RestController
public class Controller {
    
    @GetMapping("/")
    public String helloWorld() {
        return "hello world";
    }
    
    @GetMapping("/not-restricted")
    public String notRestricted() {
        return "you don't need to be logged in";
    }
    
    @GetMapping("/restricted")
    public String restricted() {
        return "if you see this you are logged in";
    }
}
