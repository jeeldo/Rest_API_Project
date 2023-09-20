package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.api.v1.model.input.CidadeInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {
	
	@ApiOperation("Lista todas as cidades")
	public CollectionModel<CidadeModel> listar();

//	//
	
	@ApiOperation("Busca uma cidade por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID da cidade inválido"
					, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada"
			, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public CidadeModel buscar(
			@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId);
	
//	//
	
	@ApiOperation("Adiciona uma cidade")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Cidade Cadastrada")
	})
	public CidadeModel adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true) CidadeInput cidadeInput);
	
//	//
	
	@ApiOperation("Atualiza uma cidade por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Cidade Atualizada"),
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada"
		, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public CidadeModel atualizar(
			@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId, 
			@ApiParam(name = "corpo", value = "Representação da cidade com novos dados", required = true) CidadeInput cidadeInput);
	
//	//
	
	@ApiOperation("Exclui uma cidade por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Cidade Excluída"),
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada"
		, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})

	public void remover(
			@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId);
}
