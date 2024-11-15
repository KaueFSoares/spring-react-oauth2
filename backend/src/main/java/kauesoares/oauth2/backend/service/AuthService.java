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

        return new AuthResDTO(
                tokenService.generateToken(
                        new TokenPayloadDTO(user.get())
                )
        );

    }
}
