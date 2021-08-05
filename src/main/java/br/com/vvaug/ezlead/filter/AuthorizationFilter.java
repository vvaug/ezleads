package br.com.vvaug.ezlead.filter;

import br.com.vvaug.ezlead.security.config.JWTSecurityService;
import br.com.vvaug.ezlead.service.SecurityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Configuration
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

    private JWTSecurityService jwtSecurityService;
    private SecurityService securityService;

    public AuthorizationFilter(JWTSecurityService jwtSecurityService, SecurityService securityService){
        this.jwtSecurityService = jwtSecurityService;
        this.securityService = securityService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      log.info("Filtering request -> {}", request.getMethod() + ": " + request.getRequestURL().toString());

      var authorization = request.getHeader("Authorization");

      if (Objects.isNull(authorization) || authorization.isEmpty()){
          log.info("Authorization HTTP Header not found!");
          filterChain.doFilter(request, response);
          return;
      }

      //remove Bearer
      authorization = authorization.substring(7);

      log.info("Authorization HTTP Header: {}", authorization);

      if (jwtSecurityService.isTokenValid(authorization)){
            authorize(authorization);
      }

      filterChain.doFilter(request, response);

    }

    protected void authorize(String jwt) {
        var user = jwtSecurityService.getUserFromJwt(jwt);
        var authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), user.getRoles());
        securityService.authenticateUser(authentication);
    }
}
