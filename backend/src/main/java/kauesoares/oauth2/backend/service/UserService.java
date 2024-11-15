package kauesoares.oauth2.backend.service;

import kauesoares.oauth2.backend.model.User;
import kauesoares.oauth2.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findByUsernameAndDeletedIsFalse(String username) {
        return userRepository.findByUsernameAndDeletedIsFalse(username);
    }
}
