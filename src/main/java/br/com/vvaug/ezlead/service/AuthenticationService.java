package br.com.vvaug.ezlead.service;

import br.com.vvaug.ezlead.document.User;
import br.com.vvaug.ezlead.dto.AuthenticationRequest;
import br.com.vvaug.ezlead.dto.AuthenticationResponse;
import br.com.vvaug.ezlead.dto.UserResponse;
import br.com.vvaug.ezlead.security.config.JWTSecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JWTSecurityService jwtSecurityService;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        log.info("Authentication Request payload: {}", authenticationRequest);

        var auth = new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword(), null);

        UserDetails user = new User();

        try {
            log.info("Calling authenticationManager to authenticate user: {}", authenticationRequest.getEmail());
            var posAuth =  authenticationManager.authenticate(auth);
            user = (User) posAuth.getPrincipal();
        } catch (BadCredentialsException ex){
            log.error(ex.getMessage());
            throw ex;
        }

        if (auth.isAuthenticated()){
            log.info("User: {} has been authenticated.", authenticationRequest.getEmail());
        }

        return jwtSecurityService.generateJWT(auth)
                .user(new UserResponse((User) user))
                .build();
    }
}
