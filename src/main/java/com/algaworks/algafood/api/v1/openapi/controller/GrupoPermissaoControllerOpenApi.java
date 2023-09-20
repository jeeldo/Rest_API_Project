package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.PermissaoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

	@ApiOperation("Lista as permissões de um grupo")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do grupo inválido"
					, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Grupo não encontrado"
			, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public CollectionModel<PermissaoModel> listar(
			@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId);
	
	
	@ApiOperation("Associa uma permissão a um grupo")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Permissão associada com sucesso"),
		@ApiResponse(responseCode = "404", description = "Grupo ou permissão não encontrado"
			, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<Void> associarPermissao(
			@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId,
			@ApiParam(value = "ID de uma permissão", example = "1", required = true) Long permissaoId);
	

	@ApiOperation("Desassocia uma permissão de um grupo")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Permissão desassociada com sucesso"),
		@ApiResponse(responseCode = "404", description = "Grupo ou permissão não encontrado"
			, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<Void> desassociarPermissao(
			@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId, 
			@ApiParam(value = "ID de uma permissão", example = "1", required = true) Long permissaoId);
}
