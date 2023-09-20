package com.algaworks.algafood.api.v1.openapi.controller;


import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

	@ApiOperation("Lista as formas de pagamentos aceitas por um restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado"
				, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public CollectionModel<FormaPagamentoModel> listar(@ApiParam(value = "ID de restaurante", example = "1", required = true) Long restauranteId);
	
	@ApiOperation("Desassocia uma forma de pagamento de um restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Forma de pagamento desassociada"),
		@ApiResponse(responseCode = "404", description = "Forma de pagamento ou restaurante não encontrado"
		, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<Void> desassociar(
			@ApiParam(value = "ID de restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "ID de Forma de Pagamento", example = "1", required = true) Long formaPagamentoId);
	
	@ApiOperation("Associa uma forma de pagamento a um restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Forma de pagamento associada"),
		@ApiResponse(responseCode = "404", description = "Forma de pagamento ou restaurante não encontrado"
		, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<Void> associar(
			@ApiParam(value = "ID de restaurante", example = "1", required = true) Long restauranteId,
			@ApiParam(value = "ID de Forma de Pagamento", example = "1", required = true) Long formaPagamentoId);
}
