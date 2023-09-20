package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.GrupoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Usuarios")
public interface UsuarioGrupoControllerOpenApi {

	@ApiOperation("Lista os grupos associados a um usuário")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID de usuário inválido"
					, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Usuário não encontrado"
			, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public CollectionModel<GrupoModel> listar(
			@ApiParam(value = "ID de um usuário", example = "1", required = true) Long usuarioId);
	
	
	@ApiOperation("Associa um usuário a um grupo")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Usuário associado com sucesso"),
		@ApiResponse(responseCode = "404", description = "Usuário ou grupo não encontrado"
			, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<Void> associarGrupo(
			@ApiParam(value = "ID de um usuário", example = "1", required = true) Long usuarioId, 
			@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId);
	
	
	@ApiOperation("Desassocia um usuário de um grupo")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Usuário desassociado com sucesso"),
		@ApiResponse(responseCode = "404", description = "Usuário ou grupo não encontrado"
			, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<Void> desassociarGrupo(
			@ApiParam(value = "ID de um usuário", example = "1", required = true) Long usuarioId, 
			@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId);
	
}
