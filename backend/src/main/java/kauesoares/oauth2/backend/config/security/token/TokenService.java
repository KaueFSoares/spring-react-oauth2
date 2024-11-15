package kauesoares.oauth2.backend.config.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kauesoares.oauth2.backend.config.properties.TokenProperties;
import kauesoares.oauth2.backend.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProperties tokenProperties;

    public String generateAccessToken(TokenPayloadDTO tokenPayloadDTO) throws JsonProcessingException {
        Algorithm algorithm = Algorithm.HMAC256(tokenProperties.getSecret());

        return JWT.create()
                .withIssuer(tokenProperties.getIssuer())
                .withSubject(tokenPayloadDTO.username())
                .withClaim("payload", new ObjectMapper().writeValueAsString(tokenPayloadDTO))
                .withExpiresAt(getExpirationDate(tokenProperties.getExpiration()))
                .sign(algorithm);
    }

    public String generateRefreshToken(TokenPayloadDTO tokenPayloadDTO) throws JsonProcessingException {
        Algorithm algorithm = Algorithm.HMAC256(tokenProperties.getSecret());

        return JWT.create()
                .withIssuer(tokenProperties.getIssuer())
                .withSubject(tokenPayloadDTO.username())
                .withClaim("payload", new ObjectMapper().writeValueAsString(tokenPayloadDTO))
                .withExpiresAt(getExpirationDate(tokenProperties.getRefreshExpiration()))
                .sign(algorithm);
    }

    public TokenPayloadDTO parseToken(String token) throws JsonProcessingException {
        Algorithm algorithm = Algorithm.HMAC256(tokenProperties.getSecret());

        DecodedJWT decodedJWT = JWT.require(algorithm)
                .withIssuer(tokenProperties.getIssuer())
                .build()
                .verify(token);

        if (isTokenExpired(decodedJWT))
            throw new JWTVerificationException("Token expired");

        return new ObjectMapper().readValue(
                decodedJWT.getClaim("payload").asString(),
                TokenPayloadDTO.class
        );
    }

    private boolean isTokenExpired(DecodedJWT decodedJWT) {
        Instant expiration = decodedJWT.getExpiresAt().toInstant();
        return expiration.isBefore(Instant.now().atZone(DateUtils.getZoneOffset()).toInstant());
    }

    private Instant getExpirationDate(Long expiration) {
        return LocalDateTime.now().plusSeconds(expiration).toInstant(DateUtils.getZoneOffset());
    }

}
