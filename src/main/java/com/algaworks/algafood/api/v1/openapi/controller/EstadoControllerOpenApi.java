package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {
	
	@ApiOperation("Lista todos os estados")
	public CollectionModel<EstadoModel> listar();
	
	@ApiOperation("Busca um estado por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do estado inválido"
					, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Estado não encontrado"
			, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public EstadoModel buscar(
			@ApiParam(value = "ID de um estado", example = "1", required = true) Long estadoId);
	
	@ApiOperation("Adiciona um novo estado")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Estado adicionado com sucesso")
	})
	public EstadoModel adicionar(
			@ApiParam(name = "corpo", value = "Representação de um novo estado", required = true) EstadoInput estadoInput);

	@ApiOperation("Atualiza um estado por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Estado Atualizado"),
		@ApiResponse(responseCode = "404", description = "Estado não encontrado"
		, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public EstadoModel atualizar(
			@ApiParam(value = "ID de um estado", example = "1", required = true) Long estadoId,
			@ApiParam(name = "corpo", value = "Representação de um novo estado", required = true) EstadoInput estadoInput);
	
	@ApiOperation("Exclui um estado por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Estado excluído com sucesso"),
		@ApiResponse(responseCode = "404", description = "Estado não encontrado"
		, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class) ))
	})
	public void remover(@ApiParam(value = "ID de um estado", example = "1", required = true) Long estadoId);

}
