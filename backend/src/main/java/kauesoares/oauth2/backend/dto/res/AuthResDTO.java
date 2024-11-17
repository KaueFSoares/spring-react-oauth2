package kauesoares.oauth2.backend.dto.res;

public record AuthResDTO(
        String accessToken,
        String refreshToken
) {
}
