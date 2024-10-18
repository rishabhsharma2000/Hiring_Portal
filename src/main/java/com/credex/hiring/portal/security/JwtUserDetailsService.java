package com.credex.hiring.portal.security;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.credex.hiring.portal.entities.User;
import com.credex.hiring.portal.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository repo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    SimpleGrantedAuthority roles = null;
    Optional<User> user = repo.findByEmail(username);
    if (!user.isPresent()) {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }

    roles = new SimpleGrantedAuthority(user.get().getRole().getRoleId());
    return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(),
        Collections.singleton(roles));
  }
}
