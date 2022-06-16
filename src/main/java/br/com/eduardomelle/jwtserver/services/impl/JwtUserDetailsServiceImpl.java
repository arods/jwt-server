package br.com.eduardomelle.jwtserver.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.eduardomelle.jwtserver.entities.Usuario;
import br.com.eduardomelle.jwtserver.security.JwtUserFactory;
import br.com.eduardomelle.jwtserver.services.UsuarioService;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UsuarioService usuarioService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Usuario> funcionario = usuarioService.buscarPorEmail(username);
    if (funcionario.isPresent()) {
      return JwtUserFactory.create(funcionario.get());
    }

    throw new UsernameNotFoundException("E-mail não encontrado.");
  }

}
