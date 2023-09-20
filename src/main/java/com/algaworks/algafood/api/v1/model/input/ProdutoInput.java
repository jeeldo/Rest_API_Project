package com.algaworks.algafood.api.v1.model.input;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoInput {

	@ApiModelProperty(example = "Xis bacon", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "Xis especial com bacon", required = true)
	@NotBlank
	private String descricao;
	
	@ApiModelProperty(example = "10", required = true)
	@PositiveOrZero
	@NotNull
	private BigDecimal preco;
	
	@ApiModelProperty(example = "true", required = true)
	@NotNull
	private Boolean ativo;
}
