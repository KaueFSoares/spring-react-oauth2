package kauesoares.oauth2.backend.config.security.token;

import kauesoares.oauth2.backend.domain.Role;
import kauesoares.oauth2.backend.model.User;

import java.util.Set;
import java.util.UUID;

public record TokenPayloadDTO(
        Long id,
        String username,
        Set<Role> roles,
        UUID refreshCode
) {
    public TokenPayloadDTO(User user) {
        this(user.getId(), user.getUsername(), user.getRoles(), null);
    }

    public TokenPayloadDTO(User user, UUID refreshCode) {
        this(user.getId(), user.getUsername(), user.getRoles(), refreshCode);
    }
}
