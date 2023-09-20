package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.UsuarioModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioResponsavelControllerOpenApi {

	@ApiOperation("Lista todos os usuários responsáveis de um restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado"
		, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public CollectionModel<UsuarioModel> listar(
			@ApiParam(value = "ID de restaurante", example = "1", required = true) Long restauranteId);
	
	@ApiOperation("Associa um usuário responsável a um restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Usuário responsável associado com sucesso"),
		@ApiResponse(responseCode = "404", description = "Restaurante ou usuário não encontrado"
		, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<Void> associarUsuarioResponsavel(
			@ApiParam(value = "ID de restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "ID de usuário", example = "1", required = true) Long usuarioId);
	
	@ApiOperation("Desassocia um usuário responsável de um restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Usuário responsável desassociado com sucesso"),
		@ApiResponse(responseCode = "404", description = "Restaurante ou usuário não encontrado"
		, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<Void> desassociarUsuarioResponsavel(
			@ApiParam(value = "ID de restaurante", example = "1", required = true) Long restauranteId,
			@ApiParam(value = "ID de usuário", example = "1", required = true) Long usuarioId);
}
