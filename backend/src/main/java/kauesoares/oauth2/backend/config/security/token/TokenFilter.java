package kauesoares.oauth2.backend.config.security.token;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kauesoares.oauth2.backend.config.security.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recoverToken(request);

        if (token != null) {
            TokenPayloadDTO tokenPayload = tokenService.parseToken(token);

            AuthUser authUser = new AuthUser(tokenPayload);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null)
            return token.replace("Bearer ", "");

        return null;
    }
}
