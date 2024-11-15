package kauesoares.oauth2.backend.repository;

import kauesoares.oauth2.backend.base.BaseRepository;
import kauesoares.oauth2.backend.model.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> findByUsernameAndDeletedIsFalse(String username);
}
