package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.domain.filter.PedidoFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula", name = "campos", type = "string", paramType = "query")
	})
	@ApiOperation("Pesquisa de pedidos")
	public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable);

	
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula"
				, name = "campos", type = "string", paramType = "query")
	})
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado"
			, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	@ApiOperation("Busca um pedido por código")
	public PedidoModel buscar(
			@ApiParam(example = "f0b02ab4-72cf-4d59-8232-4bf65521eac7", value = "Código de um pedido", required = true) String codigoPedido);
	

	@ApiOperation("Registra um pedido")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Pedido Registrado")
	})
	public PedidoModel adicionar(
			@ApiParam(name = "corpo", value = "Representação de um novo pedido", required = true) PedidoInput pedidoInput);
	

}
