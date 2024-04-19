package com.fr.eql.ai115.bellybuddyback.security;

import com.fr.eql.ai115.bellybuddyback.security.jwt.AuthEntryPointJwt;
import com.fr.eql.ai115.bellybuddyback.security.jwt.JWTAuthenticationFilter;
import com.fr.eql.ai115.bellybuddyback.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


  private CustomUserDetailsService customUserDetailsService;
  private AuthEntryPointJwt authEntryPointJwt;

  @Autowired
  public WebSecurityConfig(CustomUserDetailsService customUserDetailsService, AuthEntryPointJwt authEntryPointJwt) {
    this.customUserDetailsService = customUserDetailsService;
    this.authEntryPointJwt = authEntryPointJwt;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf((csrf) -> csrf.disable())
      .exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(authEntryPointJwt))
      .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests((authorize) -> {
        authorize.requestMatchers("/api/auth/**").permitAll();
        authorize.requestMatchers("/api/public/**").permitAll();
        authorize.requestMatchers("/api/user/**").hasAuthority("USER");
        authorize.requestMatchers("/api/admin/**").hasAuthority("ADMIN");
        authorize.anyRequest().authenticated();
      })
      .httpBasic(Customizer.withDefaults());
    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
    AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JWTAuthenticationFilter jwtAuthenticationFilter() {
    return new JWTAuthenticationFilter();
  }

}
