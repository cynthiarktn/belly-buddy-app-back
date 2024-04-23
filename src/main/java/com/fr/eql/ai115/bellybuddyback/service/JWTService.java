package com.fr.eql.ai115.bellybuddyback.service;

import com.fr.eql.ai115.bellybuddyback.security.jwt.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JWTService {
  private final JWTGenerator tokenGenerator;

  @Autowired
  public JWTService(JWTGenerator tokenGenerator) {
    this.tokenGenerator = tokenGenerator;
  }

}
