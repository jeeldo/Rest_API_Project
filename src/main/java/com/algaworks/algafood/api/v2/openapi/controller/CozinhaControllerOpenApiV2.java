package com.algaworks.algafood.api.v2.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v2.model.CozinhaModelV2;
import com.algaworks.algafood.api.v2.model.input.CozinhaInputV2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApiV2 {

	@ApiOperation("Lista todas as cozinhas com paginação")
	public PagedModel<CozinhaModelV2> listar(Pageable pageable);
	
	
	@ApiOperation("Busca uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID da cozinha inválido"
					, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Cozinha não encontrada"
			, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public CozinhaModelV2 buscar(
			@ApiParam(example = "1", value = "ID de uma cozinha", required = true) Long cozinhaId);
	
	
	@ApiOperation("Adiciona uma nova cozinha")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Coziniha cadastrada")
	})
	public CozinhaModelV2 adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma nova cozinha", required = true) CozinhaInputV2 cozinhaInput);
	
	
	@ApiOperation("Atualiza uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Cozinha atulizada"),
		@ApiResponse(responseCode = "400", description = "ID da cozinha inválido"
					, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Cozinha não encontrada"
			, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public CozinhaModelV2 atualizar(
			@ApiParam(example = "1", value = "ID de uma cozinha", required = true) Long cozinhaId, 
			@ApiParam(name = "corpo", value = "Representação de uma nova cozinha", required = true) CozinhaInputV2 cozinhaInput);
	
	
	@ApiOperation("Exclui uma cozinha")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Cozinha excluída"),
		@ApiResponse(responseCode = "404", description = "Cozinha não encontrada"
			, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public void remover(
			@ApiParam(example = "1", value = "ID de uma cozinha", required = true) Long cozinhaId);
}
