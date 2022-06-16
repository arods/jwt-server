package br.com.eduardomelle.jwtserver.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.com.eduardomelle.jwtserver.entities.Usuario;
import br.com.eduardomelle.jwtserver.security.enums.PerfilEnum;

public class JwtUserFactory {

  private JwtUserFactory() {

  }

  public static JwtUser create(Usuario usuario) {
    return new JwtUser(usuario.getId(), usuario.getEmail(), usuario.getSenha(),
        mapToGrantedAuthorities(usuario.getPerfil()));
  }

  private static Collection<? extends GrantedAuthority> mapToGrantedAuthorities(PerfilEnum perfil) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(perfil.toString()));
    return authorities;
  }

}
