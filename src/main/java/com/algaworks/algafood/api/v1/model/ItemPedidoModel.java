package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel>{

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "2")
	private Integer quantidade;
	
	@ApiModelProperty(example = "10")
	private BigDecimal precoUnitario;
	
	@ApiModelProperty(example = "20")
	private BigDecimal precoTotal;	
	
	@ApiModelProperty(example = "Sem cebola")
	private String observacao;
	
	private ProdutoModel produto;
}
