package br.com.eduardomelle.jwtserver.services;

import java.util.Optional;

import br.com.eduardomelle.jwtserver.entities.Usuario;

public interface UsuarioService {

  Optional<Usuario> buscarPorEmail(String email);

}
