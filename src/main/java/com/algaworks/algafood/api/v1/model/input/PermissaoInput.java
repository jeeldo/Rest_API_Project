package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissaoInput {

	@ApiModelProperty(example = "CONSULTAR_PEDIDOS", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "permite consultar pedidos", required = true)
	@NotBlank
	private String descricao;
}
