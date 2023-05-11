package com.example.myProject.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Practise {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello world you are seeing unauthorised response to use authorized go to http://localhost:8080/generatetoken with root user and root password";
    }
    
}
