package cz.sinko.morosystems.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cz.sinko.morosystems.repository.UserRepository;
import lombok.RequiredArgsConstructor;

/**
 * Service for loading user details.
 *
 * @author Radovan Šinko
 */
@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
