package com.toyota.user_service.controller;

import com.toyota.user_service.dto.UserMeResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping("/me")
    public UserMeResponse me(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");

        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
        @SuppressWarnings("unchecked")
        List<String> roles = realmAccess != null
                ? (List<String>) realmAccess.getOrDefault("roles", List.of())
                : List.of();

        return new UserMeResponse(username, roles);
    }
}
