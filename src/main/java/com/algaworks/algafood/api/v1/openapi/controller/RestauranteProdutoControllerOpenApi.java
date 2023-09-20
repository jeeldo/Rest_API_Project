package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.api.v1.model.input.ProdutoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {
	
	@ApiOperation("Lista todos os produtos de um determinado restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do restaurante inválido"
					, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado"
			, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public CollectionModel<ProdutoModel> listar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "Indica se deve ou não listar os produtos inativos", example = "false", defaultValue = "false") 
				Boolean incluirInativos);
	
	
	@ApiOperation("Busca um produto específico de um restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do restaurante ou produto é inválido"
					, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante ou produto não encontrado"
			, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ProdutoModel buscar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "ID de um produto", example = "1", required = true) Long produtoId);
	
	
	@ApiOperation("Cadastra um produto em um restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso"),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado"
			, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ProdutoModel adicionar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(name = "corpo", value = "Representação de um novo produto", required = true) ProdutoInput produtoInput);
	
	
	@ApiOperation("Atualiza um produto de um restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
		@ApiResponse(responseCode = "404", description = "Restaurante ou produto não encontrado"
			, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ProdutoModel atualizar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "ID de um produto", example = "1", required = true) Long produtoId,
			@ApiParam(name = "corpo", value = "Representação de um produto com novos dados", required = true) ProdutoInput produtoInput) ;

}
