package org.example.pokerium_api.service;

import org.example.pokerium_api.model.User;
import org.example.pokerium_api.repository.UserRepository;
import org.example.pokerium_api.security.UserPrincipal;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository userRepository) { this.userRepository = userRepository; }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return UserPrincipal.fromUser(u);
    }
}