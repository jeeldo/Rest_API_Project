package com.algaworks.algafood.api.v1.model.input;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {
	
	@ApiModelProperty(example = "Sem cebola", required = true)
	private String observacao;
	
	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long produtoId;
	
	@ApiModelProperty(example = "2", required = true)
	@NotNull
	@PositiveOrZero
	private Long quantidade;

}
