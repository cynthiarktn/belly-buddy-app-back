package com.fr.eql.ai115.bellybuddyback.security.jwt;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {
  private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
  private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

  public static String generateNewSecretKey() {
    byte[] randomBytes = new byte[64]; // 512 bits
    secureRandom.nextBytes(randomBytes);
    return base64Encoder.encodeToString(randomBytes);
  }
}
