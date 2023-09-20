package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonFilter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@JsonFilter("pedidoFilter")
@Relation(collectionRelation = "pedidos")
@Getter
@Setter
public class PedidoResumoModel extends RepresentationModel<PedidoResumoModel>{

	@ApiModelProperty(example = "f0b02ab4-72cf-4d59-8232-4bf65521eac7")
	private String codigo;
	
	@ApiModelProperty(example = "20.00")
	private BigDecimal subtotal;
	
	@ApiModelProperty(example = "05.00")
	private BigDecimal taxaFrete;
	
	@ApiModelProperty(example = "25.00")
	private BigDecimal valorTotal;
	
	@ApiModelProperty(example = "2023-01-24T01:09:21Z")
	private OffsetDateTime dataCriacao;
	
	private RestauranteApenasNomeModel restaurante;
	
	private UsuarioModel cliente;
	
	@ApiModelProperty(example = "CRIADO")
	private String status;
}
