package br.com.eduardomelle.jwtserver.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eduardomelle.jwtserver.entities.Usuario;
import br.com.eduardomelle.jwtserver.repositories.UsuarioRepository;
import br.com.eduardomelle.jwtserver.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  public Optional<Usuario> buscarPorEmail(String email) {
    return Optional.ofNullable(this.usuarioRepository.findByEmail(email));
  }

}
