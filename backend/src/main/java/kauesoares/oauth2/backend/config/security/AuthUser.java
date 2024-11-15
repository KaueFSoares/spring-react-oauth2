package kauesoares.oauth2.backend.config.security;

import kauesoares.oauth2.backend.config.security.token.TokenPayloadDTO;
import kauesoares.oauth2.backend.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
public class AuthUser implements UserDetails {
    private final User user;

    public AuthUser(TokenPayloadDTO tokenPayload) {
        this.user = User.builder()
                .id(tokenPayload.id())
                .username(tokenPayload.username())
                .roles(tokenPayload.roles())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.toString())).toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
