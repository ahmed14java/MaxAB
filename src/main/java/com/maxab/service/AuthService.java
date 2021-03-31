package com.maxab.service;

import com.maxab.service.dto.AuthDTO;
import com.maxab.service.dto.LoginDTO;
import com.maxab.service.dto.RegisterDTO;
import org.keycloak.representations.AccessTokenResponse;

public interface AuthService {

    AccessTokenResponse login(LoginDTO loginDTO);

    AuthDTO create(RegisterDTO registerDTO);
}
