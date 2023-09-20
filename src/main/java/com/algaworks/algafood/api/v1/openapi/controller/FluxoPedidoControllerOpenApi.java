package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.algaworks.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Pedidos")
public interface FluxoPedidoControllerOpenApi {
	
	@ApiOperation("Confirma um pedido por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Pedido Confirmado"),
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado"
		, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<Void> confirmar(
			@ApiParam(example = "f0b02ab4-72cf-4d59-8232-4bf65521eac7", value = "Código de um pedido", required = true) String codigoPedido);
	
	
	@ApiOperation("Entrega um pedido por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Pedido Entregue"),
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado"
		, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<Void> entregar(
			@ApiParam(example = "f0b02ab4-72cf-4d59-8232-4bf65521eac7", value = "Código de um pedido", required = true) String codigoPedido);
	
	
	@ApiOperation("Cancela um pedido por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Pedido Cancelado"),
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado"
		, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<Void> cancelar(
			@ApiParam(example = "f0b02ab4-72cf-4d59-8232-4bf65521eac7", value = "Código de um pedido", required = true) String codigoPedido);

}
