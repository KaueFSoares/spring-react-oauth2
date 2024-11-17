package kauesoares.oauth2.backend.config.security.oauth2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kauesoares.oauth2.backend.dto.res.AuthResDTO;
import kauesoares.oauth2.backend.model.User;
import kauesoares.oauth2.backend.service.AuthService;
import kauesoares.oauth2.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;
    private final AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // for google only (github not working)
        Optional<User> user = userService.findByUsernameAndDeletedIsFalse(oAuth2User.getAttribute("email"));

        if (user.isEmpty()) {
            response.sendRedirect(String.format("http://localhost:5173/success?error=%s", "User not found"));
            return;
        }

        AuthResDTO authResDTO = authService.generateTokens(user.get());

        response.sendRedirect(String.format("http://localhost:5173/success?access=%s&refresh=%s", authResDTO.accessToken(), authResDTO.refreshToken()));
    }
}
