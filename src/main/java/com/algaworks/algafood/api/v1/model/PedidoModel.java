package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "pedidos")
@Getter
@Setter
public class PedidoModel extends RepresentationModel<PedidoModel>{

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
	
	@ApiModelProperty(example = "2023-01-24T01:09:21Z")
	private OffsetDateTime dataConfirmacao;
	
	@ApiModelProperty(example = "2023-01-24T01:09:21Z")
	private OffsetDateTime dataCancelamento;	
	
	@ApiModelProperty(example = "2023-01-24T01:09:21Z")
	private OffsetDateTime dataEntrega;	
	
	private RestauranteApenasNomeModel restaurante;
	
	private UsuarioModel cliente;
	
	private EnderecoModel enderecoEntrega;
	
	private FormaPagamentoModel formaPagamento;
	
	private List<ItemPedidoModel> itens;
	
	@ApiModelProperty(example = "CRIADO")
	private String status;
}

















