package com.maxab.resource;

import com.maxab.service.AuthService;
import com.maxab.service.dto.AuthDTO;
import com.maxab.service.dto.LoginDTO;
import com.maxab.service.dto.RegisterDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * {@code POST } : Create new user.
     * @param registerDTO the user to create.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthDTO> register(@RequestBody RegisterDTO registerDTO) {
        AuthDTO authDTO = authService.create(registerDTO);
        return ResponseEntity.ok().body(authDTO);
    }

    /**
     * {@code POST } : get authentication.
     * @param loginDTO the user credentials.
     */
    @PostMapping("/signin")
    public ResponseEntity<AccessTokenResponse> authenticate(@RequestBody LoginDTO loginDTO){
        AccessTokenResponse response = authService.login(loginDTO);
        return ResponseEntity.ok().body(response);
    }
}
