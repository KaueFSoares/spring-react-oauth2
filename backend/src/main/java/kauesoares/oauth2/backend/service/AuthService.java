package kauesoares.oauth2.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import kauesoares.oauth2.backend.config.security.token.TokenPayloadDTO;
import kauesoares.oauth2.backend.config.security.token.TokenService;
import kauesoares.oauth2.backend.dto.req.AuthReqDTO;
import kauesoares.oauth2.backend.dto.res.AuthResDTO;
import kauesoares.oauth2.backend.model.User;
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

        UUID refreshCode = UUID.randomUUID();

        user.get().setRefreshCode(refreshCode);

        userService.save(user.get());

        return new AuthResDTO(
                tokenService.generateAccessToken(
                        new TokenPayloadDTO(user.get())
                ),
                tokenService.generateRefreshToken(
                        new TokenPayloadDTO(user.get(), refreshCode)
                )
        );

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

        return new AuthResDTO(
                tokenService.generateAccessToken(
                        new TokenPayloadDTO(user.get())
                ),
                tokenService.generateRefreshToken(
                        new TokenPayloadDTO(user.get(), UUID.randomUUID())
                )
        );
    }
}
