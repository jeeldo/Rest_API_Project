package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Relation(collectionRelation = "restaurantes")
public class RestauranteBasicoModel extends RepresentationModel<RestauranteBasicoModel>{

	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Bar do Zé")
	private String nome;
	
	@ApiModelProperty(example = "09.00")
	private BigDecimal taxaFrete;
	
	private CozinhaModel cozinha;
	
}
