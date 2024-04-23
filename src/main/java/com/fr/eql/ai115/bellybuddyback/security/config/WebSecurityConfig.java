package com.fr.eql.ai115.bellybuddyback.security.config;

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

  private AuthEntryPointJwt authEntryPointJwt;
  private CustomUserDetailsService userDetailsService;

  @Autowired
  public WebSecurityConfig(AuthEntryPointJwt authEntryPointJwt, CustomUserDetailsService userDetailsService) {
    this.authEntryPointJwt = authEntryPointJwt;
    this.userDetailsService = userDetailsService;
  }
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf((csrf) -> csrf.disable())
      .exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(authEntryPointJwt))
      .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests((authorize) -> {
        authorize.requestMatchers("/api/user/**").authenticated();
        authorize.requestMatchers("/api/public/**").permitAll()
          .anyRequest().permitAll();
      })
      .httpBasic(Customizer.withDefaults());
    http.addFilterBefore((jwtAuthenticationFilter()), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
  @Bean
  public AuthenticationManager authenticationManager(
    AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JWTAuthenticationFilter jwtAuthenticationFilter() {
    return new JWTAuthenticationFilter();
  }
}
