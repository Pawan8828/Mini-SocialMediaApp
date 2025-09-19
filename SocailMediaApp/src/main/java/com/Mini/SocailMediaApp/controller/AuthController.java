package com.Mini.SocailMediaApp.controller;

import com.Mini.SocailMediaApp.dto.AuthRequest;
import com.Mini.SocailMediaApp.dto.AuthResponse;
import com.Mini.SocailMediaApp.dto.RegisterRequest;
import com.Mini.SocailMediaApp.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        String jwt = authService.register(request);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        String jwt = authService.login(request);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
