package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "produtos")
@Getter
@Setter
public class ProdutoModel extends RepresentationModel<ProdutoModel>{

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Xis Bacon")
	private String nome;
	
	@ApiModelProperty(example = "Xis especial com Bacon")
	private String descricao;
	
	@ApiModelProperty(example = "10")
	private BigDecimal preco;
	
	@ApiModelProperty(example = "true")
	private boolean ativo;
}
