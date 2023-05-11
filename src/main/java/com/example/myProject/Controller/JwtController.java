package com.example.myProject.Controller;

import com.example.myProject.Model.JwtRequest;
import com.example.myProject.Response.JwtResponse;
import com.example.myProject.Service.CustomUserDetailsService;
import com.example.myProject.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/generatetoken")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        System.out.println(jwtRequest);
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            throw new Exception("User not found with name " + jwtRequest.getUsername());
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            throw new Exception("Bad Credentials");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        System.out.println("JWT TOKEN " + token);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
