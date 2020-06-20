package ru.otus.spring.petrova.config;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  public void configure( WebSecurity web ) {
    web.ignoring().antMatchers( "/" );
  }

  @Override
  public void configure( HttpSecurity http ) throws Exception {
    http.csrf().disable()
        .authorizeRequests().antMatchers("/books").permitAll()
        .and()
        .authorizeRequests().antMatchers("/books/add").hasRole("ADMIN")
        .and()
        .authorizeRequests().antMatchers("/books/*").hasRole("ADMIN")
        .and()
        .authorizeRequests().antMatchers("/books/delete/*").hasRole("ADMIN")
        .and()
//        .authorizeRequests().antMatchers("/books/*/comments").hasAnyRole("ADMIN", "USER")
//        .and()
//        .authorizeRequests().antMatchers("/books/*/comments/add").hasAnyRole("ADMIN", "USER")
//        .and()
        .authorizeRequests().antMatchers("/books/**").authenticated()
        .and().anonymous().authorities("ROLE_ANONYMOUS").principal(User.class)
        .and()
        .formLogin()
        .defaultSuccessUrl("/books")
        .and().rememberMe();

    http.rememberMe()
        .key("SecretWorld")
        .tokenValiditySeconds(50000);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
