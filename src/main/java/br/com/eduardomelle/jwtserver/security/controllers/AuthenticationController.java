package br.com.eduardomelle.jwtserver.security.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardomelle.jwtserver.entities.Usuario;
import br.com.eduardomelle.jwtserver.repositories.UsuarioRepository;
import br.com.eduardomelle.jwtserver.response.Response;
import br.com.eduardomelle.jwtserver.security.dto.JwtAuthenticationDto;
import br.com.eduardomelle.jwtserver.security.dto.TokenDto;
import br.com.eduardomelle.jwtserver.security.dto.UserDto;
import br.com.eduardomelle.jwtserver.security.dto.ValidateTokenDtoResponse;
import br.com.eduardomelle.jwtserver.utils.JwtTokenUtil;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

	private static final String BEARER_PREFIX = "Bearer";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping
	public ResponseEntity<Response<TokenDto>> gerarTokenJwt(@Valid @RequestBody JwtAuthenticationDto authenticationDto,
			BindingResult result) throws AuthenticationException {
		Response<TokenDto> response = new Response<>();

		if (result.hasErrors()) {
			log.error("Erro validando lançamento: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		log.info("Gerando token para o email: {}", authenticationDto.getEmail());

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationDto.getEmail(), authenticationDto.getSenha()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDto.getEmail());
		String token = jwtTokenUtil.obterToken(userDetails);
		response.setData(new TokenDto(token));

		return ResponseEntity.ok(response);
	}

	@PostMapping("/validateToken")
	public ResponseEntity<ValidateTokenDtoResponse> validateToken(@RequestParam(required = true) String token) {
		/*
		 * if (token != null && token.startsWith(BEARER_PREFIX)) { token =
		 * token.substring(7); }
		 */

		ValidateTokenDtoResponse validateTokenDtoResponse = new ValidateTokenDtoResponse();

		boolean tokenValido = jwtTokenUtil.tokenValido(token);
		if (tokenValido) {
			String username = jwtTokenUtil.getUsernameFromToken(token);

			Usuario usuario = usuarioRepository.findByEmail(username);

			UserDto userDTO = new UserDto();
			userDTO.setId(usuario.getId());

			validateTokenDtoResponse.setUserDto(userDTO);

			return ResponseEntity.ok(validateTokenDtoResponse);
		}

		validateTokenDtoResponse.setMessage(HttpStatus.UNAUTHORIZED.getReasonPhrase());

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(validateTokenDtoResponse);
	}

}
