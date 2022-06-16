package br.com.eduardomelle.jwtserver.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.eduardomelle.jwtserver.security.enums.PerfilEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class Usuario implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "senha", nullable = false)
  private String senha;

  @Enumerated(EnumType.STRING)
  @Column(name = "perfil", nullable = false)
  private PerfilEnum perfil;

}
