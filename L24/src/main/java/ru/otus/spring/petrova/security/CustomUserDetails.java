package ru.otus.spring.petrova.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.otus.spring.petrova.domain.User;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
  private User user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return user.getAuthority()
        .stream()
        .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
        .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return user.getEnabled();
  }
}
