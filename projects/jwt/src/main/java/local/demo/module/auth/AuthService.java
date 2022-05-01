package local.demo.module.auth;

import java.time.Instant;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;

import local.demo.module.auth.dto.AuthenticationResponse;
import local.demo.module.auth.dto.LoginRequest;
import local.demo.module.auth.dto.RefreshTokenRequest;
import local.demo.module.auth.dto.RegisterRequest;
import local.demo.module.auth.entity.User;
import local.demo.module.auth.entity.UserRepo;
import local.demo.config.JwtProvider;


@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    public User getCurrentUser() {
        Jwt principal = (Jwt) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();

        return userRepo.findByUsername(principal.getSubject())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "username: %s".formatted(principal.getSubject()))
                );
    }

    @Transactional
    protected void signup(RegisterRequest registerRequest) {

        if (userRepo.findByUsername(registerRequest.getUsername()).isPresent()
                || userRepo.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new AuthException("%s || %s already exists".formatted(
                    registerRequest.getUsername(), registerRequest.getEmail())
            );
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .createdAt(Instant.now())
                .enabled(true)
                .build();
        userRepo.save(user);
    }

    protected AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        Instant now = Instant.now();
        String token = jwtProvider.generateToken(authenticate, now);
        String refreshToken = refreshTokenService.generateRefreshToken().getToken();

        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshToken)
                .expiresAt(now.plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }

    protected AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());

        Instant now = Instant.now();
        String username = refreshTokenRequest.getUsername();
        String token = jwtProvider.generateTokenWithUsername(username, now);
        String refreshToken = refreshTokenRequest.getRefreshToken();

        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshToken)
                .expiresAt(now.plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(username)
                .build();
    }
}
