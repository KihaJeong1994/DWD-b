package com.dwd.dwdb.service.auth;

import com.dwd.dwdb.dto.AuthenticationRequest;
import com.dwd.dwdb.dto.AuthenticationResponse;
import com.dwd.dwdb.dto.RegisterRequest;
import com.dwd.dwdb.enums.Role;
import com.dwd.dwdb.model.user.User;
import com.dwd.dwdb.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .userId(request.getUserId())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserId(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow();
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
