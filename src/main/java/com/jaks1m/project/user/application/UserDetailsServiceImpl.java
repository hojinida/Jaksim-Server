package com.jaks1m.project.user.application;

import com.jaks1m.project.common.exception.ErrorCode;
import com.jaks1m.project.common.exception.CustomException;
import com.jaks1m.project.user.domain.User;
import com.jaks1m.project.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        return user.get();
    }

}
