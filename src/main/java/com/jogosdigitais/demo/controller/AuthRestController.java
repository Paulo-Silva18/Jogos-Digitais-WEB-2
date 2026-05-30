package com.jogosdigitais.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    @GetMapping("/user")
    public Map<String, Object> getAuthenticatedUser(Authentication authentication) {
        Map<String, Object> userDetails = new HashMap<>();
        if (authentication != null && authentication.isAuthenticated()) {
            userDetails.put("username", authentication.getName());
            userDetails.put("roles", authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
        }
        return userDetails;
    }
}
