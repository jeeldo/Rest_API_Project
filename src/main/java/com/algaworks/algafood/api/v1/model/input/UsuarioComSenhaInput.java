package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioComSenhaInput {
	
	@ApiModelProperty(example = "1234", required = true)
	@NotBlank
	private String senha;
	
	@ApiModelProperty(example = "Jean Mello", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "jean@hotmail.com", required = true)
	@NotBlank
	@Email
	private String email;

}
