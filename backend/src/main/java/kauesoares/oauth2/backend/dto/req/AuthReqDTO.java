package kauesoares.oauth2.backend.dto.req;

import jakarta.validation.constraints.NotEmpty;

public record AuthReqDTO(
        @NotEmpty
        String username,

        @NotEmpty
        String password
) {
}
