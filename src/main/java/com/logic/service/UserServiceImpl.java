package com.logic.service;

import com.logic.entity.Users;
import com.logic.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Users users = usersRepository.findByUsername(username)
              .orElseThrow(()-> new  UsernameNotFoundException("Invalid username"));

      return User.withUsername(username)
              .password(users.getPassword())
              .disabled(!users.isActive())
              .build();
    }
}
