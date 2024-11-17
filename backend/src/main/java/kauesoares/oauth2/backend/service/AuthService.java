package kauesoares.oauth2.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import kauesoares.oauth2.backend.config.security.token.TokenPayloadDTO;
import kauesoares.oauth2.backend.config.security.token.TokenService;
import kauesoares.oauth2.backend.dto.req.AuthReqDTO;
import kauesoares.oauth2.backend.dto.res.AuthResDTO;
import kauesoares.oauth2.backend.model.User;
import kauesoares.oauth2.backend.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthResDTO login(AuthReqDTO authReqDTO) throws JsonProcessingException {
        Optional<User> user = userService.findByUsernameAndDeletedIsFalse(authReqDTO.username());

        if (user.isEmpty())
            throw new UsernameNotFoundException("Invalid credentials");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                authReqDTO.username(),
                authReqDTO.password()
        );

        authenticationManager.authenticate(token);

        return generateTokens(user.get());
    }

    public AuthResDTO refresh(String refreshToken) throws JsonProcessingException {
        TokenPayloadDTO tokenPayload = tokenService.parseToken(refreshToken.replace("Bearer ", ""));

        if (tokenPayload.refreshCode() == null)
            throw new IllegalArgumentException("Invalid token");

        Optional<User> user = userService.findByUsernameAndDeletedIsFalse(tokenPayload.username());

        if (user.isEmpty())
            throw new UsernameNotFoundException("Invalid credentials");

        if (!user.get().getRefreshCode().equals(tokenPayload.refreshCode()))
            throw new IllegalArgumentException("Invalid token");

        return generateTokens(user.get());
    }

    public AuthResDTO generateTokens(User user) throws JsonProcessingException {
        UUID refreshCode = UUID.randomUUID();

        user.setRefreshCode(refreshCode);

        userService.save(user);

        String accessToken = tokenService.generateAccessToken(
                new TokenPayloadDTO(user)
        );

        String refreshToken = tokenService.generateRefreshToken(
                new TokenPayloadDTO(user, refreshCode)
        );

        return new AuthResDTO(accessToken, refreshToken);
    }

    public void logout() {
        User user = this.userService.findByUsernameAndDeletedIsFalse(AuthUtil.getUserName())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));

        user.setRefreshCode(null);
        this.userService.save(user);
    }
}
