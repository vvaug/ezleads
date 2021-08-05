package br.com.vvaug.ezlead.service;

import br.com.vvaug.ezlead.document.User;
import br.com.vvaug.ezlead.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final UserRepository userRepository;

    public User getAuthenticatedUser(){
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())
                .orElseThrow(() -> new UsernameNotFoundException("User not found on repository"));
    }

    public void authenticateUser(Authentication authentication){
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
