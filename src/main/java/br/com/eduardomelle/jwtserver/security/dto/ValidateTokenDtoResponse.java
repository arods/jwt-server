/**
 * 
 */
package br.com.eduardomelle.jwtserver.security.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author eduardo
 *
 */
@Data
public class ValidateTokenDtoResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private UserDto userDto;

	private String message;

}
