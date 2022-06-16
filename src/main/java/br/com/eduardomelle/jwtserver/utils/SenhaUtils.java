package br.com.eduardomelle.jwtserver.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SenhaUtils {

  public static String gerarBCrypt(String senha) {
    if (senha == null) {
      return senha;
    }

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return bCryptPasswordEncoder.encode(senha);
  }

}
