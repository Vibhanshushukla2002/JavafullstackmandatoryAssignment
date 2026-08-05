package com.StudentLibrary.Studentlibrary.Security;

import com.StudentLibrary.Studentlibrary.Model.User;
import com.StudentLibrary.Studentlibrary.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user;

        if (username.contains("@")) {
            user = userRepository.findByEmail(username);
        } else {
            user = userRepository.findByUsername(username);
        }
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }

        return new CustomUserDetails(user);
    }
}