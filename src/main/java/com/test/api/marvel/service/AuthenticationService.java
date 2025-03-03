package com.test.api.marvel.service;

import com.test.api.marvel.dto.security.LoginRequest;
import com.test.api.marvel.dto.security.LoginResponse;
import com.test.api.marvel.persistence.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    @Autowired
    private HttpSecurity httpSecurity;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public LoginResponse login(LoginRequest loginRequest) {

        UserDetails user = userDetailsService.loadUserByUsername(loginRequest.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, loginRequest.getPassword(), user.getAuthorities());
        authenticationManager.authenticate(authentication);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setJwt(jwtService.generateToken(user, generateExtraClaims(user)));

        return loginResponse;
    }

    private Map<String, Object> generateExtraClaims(UserDetails user) {
        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("role", ((User) user).getRole().getName().name());
        extraClaims.put("authorities", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        return extraClaims;
    }

    public void logout() {
        try {
            httpSecurity.logout(httpSecurityLogoutConfigurer ->
                    httpSecurityLogoutConfigurer.deleteCookies("JSESSIONID").clearAuthentication(true).invalidateHttpSession(true));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
