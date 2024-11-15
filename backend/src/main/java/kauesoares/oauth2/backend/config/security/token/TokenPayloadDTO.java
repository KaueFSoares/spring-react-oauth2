package kauesoares.oauth2.backend.config.security.token;

import kauesoares.oauth2.backend.domain.Role;
import kauesoares.oauth2.backend.model.User;

import java.util.Set;

public record TokenPayloadDTO(
        Long id,
        String username,
        Set<Role> roles
) {
    public static TokenPayloadDTO fromUser(User user) {
        return new TokenPayloadDTO(user.getId(), user.getUsername(), user.getRoles());
    }
}