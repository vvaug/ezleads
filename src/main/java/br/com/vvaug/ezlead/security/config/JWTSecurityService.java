package br.com.vvaug.ezlead.security.config;

import br.com.vvaug.ezlead.document.User;
import br.com.vvaug.ezlead.dto.AuthenticationResponse;
import br.com.vvaug.ezlead.dto.ExpirationType;
import br.com.vvaug.ezlead.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Profile("!dev")
@Service
@RequiredArgsConstructor
@Slf4j
public class JWTSecurityService {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public boolean isTokenValid(String authorization) {

        log.info("Validating the JWT Token");

        try {
            Jwts.parserBuilder()
                    .build()
                    .parse(authorization);

            log.info("Valid JWT!");

            return true;
        } catch (Exception e){
            log.error("Not valid JWT: {}", e.getMessage());
            return false;
        }
    }

    public AuthenticationResponse.AuthenticationResponseBuilder generateJWT(UsernamePasswordAuthenticationToken auth) {
        log.info("Generating JWT token for user: {}", auth.getPrincipal().toString());

        var issuedAt = new Date();
        var expiresAt = addExpiration(issuedAt,30);

        return AuthenticationResponse.builder()
                .jwt(Jwts.builder()
                .setIssuer("ez-leads-api-v1")
                .setIssuedAt(issuedAt)
                .setSubject(auth.getPrincipal().toString())
                .setExpiration(expiresAt)
                .compact())
                .expiresIn(30)
                .type(ExpirationType.MINUTE);
    }

    //TODO upgrade logic
    protected Date addExpiration(Date issuedAt, int minutes){
        var calIssuedAt = Calendar.getInstance();
        calIssuedAt.setTime(issuedAt);
        calIssuedAt.add(Calendar.MINUTE, minutes);
        log.info("JWT will expires in: {}", calIssuedAt.getTime());
        return calIssuedAt.getTime();
    }

    public User getUserFromJwt(String jwt) {

        var body = (Claims) Jwts.parserBuilder()
                .build()
                .parse(jwt)
                .getBody();

        var user = userRepository.findByEmail(body.getSubject());

        if (user.isPresent()){
            log.info("JWT User: {}", user.get());
            return user.get();
        }

        //TODO upgrade this exception
        throw new RuntimeException("Cannot get user from JWT.");
    }
}
