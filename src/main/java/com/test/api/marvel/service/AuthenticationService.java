package com.test.api.marvel.service;

import com.test.api.marvel.dto.security.LoginRequest;
import com.test.api.marvel.dto.security.LoginResponse;
import com.test.api.marvel.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final HttpSecurity http;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public LoginResponse login(LoginRequest loginRequest) {
        // Cargar el usuario desde el servicio de detalles
        UserDetails user = userDetailsService.loadUserByUsername(loginRequest.getUsername());

        // Crear el token de autenticación
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user, loginRequest.getPassword(), user.getAuthorities()
        );

        // Autenticar al usuario
        Authentication authenticated = authenticationManager.authenticate(authentication);

        // Establecer la autenticación en el SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authenticated);

        // Generar el JWT
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));

        // Crear la respuesta
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setJwt(jwt);

        return loginResponse;
    }


    private Map<String, Object> generateExtraClaims(UserDetails user) {
        Map<String, Object> extraClaims = new HashMap<>();

        String roleName = ((User) user).getRole().getName().name();
        extraClaims.put("role", roleName);
        extraClaims.put("authorities", user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return extraClaims;
    }

    public void logout() {
        try {
            http.logout(logoutConfig -> logoutConfig.deleteCookies("JSESSIONID")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public UserDetails getUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof UsernamePasswordAuthenticationToken authenticationToken)) {
            throw new AuthenticationCredentialsNotFoundException("Requerida autenticacion completa");
        }

        return (UserDetails) authenticationToken.getPrincipal();
    }
}