package kauesoares.oauth2.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import kauesoares.oauth2.backend.dto.req.AuthReqDTO;
import kauesoares.oauth2.backend.dto.res.AuthResDTO;
import kauesoares.oauth2.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResDTO> login(
            @RequestBody
            @Valid
            AuthReqDTO authReqDTO
    ) throws JsonProcessingException {
        return ResponseEntity.ok(
                this.authService.login(authReqDTO)
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResDTO> refresh(
            @RequestHeader("Authorization")
            String refreshToken
    ) throws JsonProcessingException {
        return ResponseEntity.ok(
                this.authService.refresh(refreshToken)
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        this.authService.logout();

        return ResponseEntity.ok().build();
    }


}
