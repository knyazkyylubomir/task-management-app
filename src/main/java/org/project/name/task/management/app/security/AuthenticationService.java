package org.project.name.task.management.app.security;

import lombok.RequiredArgsConstructor;
import org.project.name.task.management.app.dto.user.UserLoginRequest;
import org.project.name.task.management.app.dto.user.UserLoginResponseDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserLoginResponseDto authenticate(UserLoginRequest request) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                )
        );
        String token = jwtUtil.generateToken(request.getUsername());
        return new UserLoginResponseDto(token);
    }
}
