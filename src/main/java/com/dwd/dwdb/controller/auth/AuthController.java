package com.dwd.dwdb.controller.auth;

import com.dwd.dwdb.dto.AuthenticationRequest;
import com.dwd.dwdb.dto.AuthenticationResponse;
import com.dwd.dwdb.dto.ErrorResponse;
import com.dwd.dwdb.dto.RegisterRequest;
import com.dwd.dwdb.exception.CustomRuntimeException;
import com.dwd.dwdb.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ) {
        AuthenticationResponse authResponse=null;
        try{
            authResponse = authService.register(request);
        } catch (CustomRuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getCode(),e.getMessage()));
        }

        return ResponseEntity.ok(authResponse);
    }
    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse authResponse = authService.authenticate(request);
        return ResponseEntity.ok(authResponse);
    }
}
