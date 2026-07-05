package com.macronnect.sales_api.controller;

import com.macronnect.sales_api.model.dto.auth.LoginRequest;
import com.macronnect.sales_api.model.dto.auth.LoginResponse;
import com.macronnect.sales_api.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Operations related to log in the application")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Operation(
            summary = "Log in",
            description = "Authorizes an existing user to log in the application."
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(

                        new UsernamePasswordAuthenticationToken(

                                request.getUsername(),
                                request.getPassword()

                        )
                );

        String token = jwtTokenProvider.generateToken(
                authentication.getName()
        );

        return ResponseEntity.ok(
                new LoginResponse(token)
        );
    }
}
