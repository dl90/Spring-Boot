package local.demo.module.auth;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import local.demo.module.auth.entity.RefreshToken;
import local.demo.module.auth.entity.RefreshTokenRepo;


@Service
@AllArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepo refreshTokenRepo;

    protected RefreshToken generateRefreshToken() {
        // @TODO del existing token on re-auth
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .createdDate(Instant.now())
                .build();
        return refreshTokenRepo.save(refreshToken);
    }

    public void validateRefreshToken(String token) throws AuthException {
        refreshTokenRepo.findByToken(token)
                .orElseThrow(() -> new AuthException("Invalid refresh Token"));
    }

    public void deleteRefreshToken(String token) throws IllegalArgumentException {
        refreshTokenRepo.deleteByToken(token);
    }
}
