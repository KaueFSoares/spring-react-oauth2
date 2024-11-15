package kauesoares.oauth2.backend.config.security;

import kauesoares.oauth2.backend.model.User;
import kauesoares.oauth2.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.findByUsernameAndDeletedIsFalse(username);

        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new AuthUser(user.get());
    }
}
