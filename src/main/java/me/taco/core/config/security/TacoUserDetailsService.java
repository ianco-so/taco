package me.taco.core.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import me.taco.api.repository.TacoUserRepository;

@Service
public class TacoUserDetailsService implements UserDetailsService {

  @Autowired
  private TacoUserRepository userRepo;


  
  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
    var user = userRepo.findByUsername(username);
    if (user != null) {
      return user;
    }
    throw new UsernameNotFoundException("User '" + username + "' not found");
  }

}