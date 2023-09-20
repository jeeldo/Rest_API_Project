package com.algaworks.algafood.api.v2.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApiV2 {

	@ApiOperation("Lista todas as cidades")
	public CollectionModel<CidadeModelV2> listar();
	
	
	@ApiOperation("Busca uma cidade por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID da cidade inválido"
					, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada"
			, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public CidadeModelV2 buscar(
			@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId);
	
	
	@ApiOperation("Adiciona uma nova cidade")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Cidade Cadastrada")
	})
	public CidadeModelV2 adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true) CidadeInputV2 cidadeInput);
	
	
	@ApiOperation("Atualiza uma cidade por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Cidade Atualizada"),
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada"
		, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public CidadeModelV2 atualizar(
			@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId, 
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true) CidadeInputV2 cidadeInput);
}
