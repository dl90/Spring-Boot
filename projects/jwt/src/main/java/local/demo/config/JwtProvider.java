package local.demo.config;

import java.time.Instant;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtEncoder jwtEncoder;

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    public String generateToken(Authentication authentication, Instant nowTimestamp) {
        User principal = (User) authentication.getPrincipal();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtIssuer)
                .issuedAt(nowTimestamp)
                .expiresAt(nowTimestamp.plusMillis(jwtExpirationInMillis))
                .subject(principal.getUsername())
                .claim("scope", "ROLE_USER")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateTokenWithUsername(String username, Instant nowTimestamp) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtIssuer)
                .issuedAt(nowTimestamp)
                .expiresAt(nowTimestamp.plusMillis(jwtExpirationInMillis))
                .subject(username)
                .claim("scope", "ROLE_USER")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
}
