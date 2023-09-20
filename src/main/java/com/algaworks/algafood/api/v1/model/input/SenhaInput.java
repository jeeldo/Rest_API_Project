package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SenhaInput {
	
	@ApiModelProperty(example = "1234", required = true)
	@NotBlank
	private String senhaAtual;
	
	@ApiModelProperty(example = "0000", required = true)
	@NotBlank
	private String novaSenha;

}
