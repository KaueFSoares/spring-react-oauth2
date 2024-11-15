package kauesoares.oauth2.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import kauesoares.oauth2.backend.dto.req.AuthReqDTO;
import kauesoares.oauth2.backend.dto.res.AuthResDTO;
import kauesoares.oauth2.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<AuthResDTO> login(
            @RequestBody
            @Valid
            AuthReqDTO authReqDTO
    ) throws JsonProcessingException {
        return ResponseEntity.ok(
                this.authService.login(authReqDTO)
        );
    }

}
