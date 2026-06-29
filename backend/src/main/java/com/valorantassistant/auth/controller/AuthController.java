package com.valorantassistant.auth.controller;

import com.valorantassistant.auth.dto.CurrentUserResponse;
import com.valorantassistant.auth.dto.LoginRequest;
import com.valorantassistant.auth.dto.LoginResponse;
import com.valorantassistant.auth.dto.SessionContextResponse;
import com.valorantassistant.auth.service.AuthService;
import com.valorantassistant.common.api.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpServletRequest) {
        return ApiResponse.success(authService.login(request, httpServletRequest.getRemoteAddr()));
    }

    @GetMapping("/me")
    public ApiResponse<CurrentUserResponse> me(Authentication authentication) {
        return ApiResponse.success(authService.getCurrentUser(authentication.getName()));
    }

    @GetMapping("/session-context")
    public ApiResponse<SessionContextResponse> sessionContext(Authentication authentication) {
        return ApiResponse.success(authService.getSessionContext(authentication.getName()));
    }
}
