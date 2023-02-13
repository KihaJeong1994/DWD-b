package com.dwd.dwdb.service.auth;

import com.dwd.dwdb.dto.AuthenticationRequest;
import com.dwd.dwdb.dto.AuthenticationResponse;
import com.dwd.dwdb.dto.RegisterRequest;

public interface AuthService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
