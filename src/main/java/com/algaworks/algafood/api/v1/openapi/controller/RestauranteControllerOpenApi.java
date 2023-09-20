package com.algaworks.algafood.api.v1.openapi.controller;


import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import com.algaworks.algafood.api.v1.openapi.model.RestauranteBasicoModelOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

	@ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoModelOpenApi.class)
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nome da projeção de pedidos"
				, name = "projecao", type = "string", paramType = "query", allowableValues = "apenas-nome")
	})	
	public CollectionModel<RestauranteBasicoModel> listar();
	
	@ApiIgnore
	@ApiOperation(value = "Lista restaurantes", hidden = true)
	public CollectionModel<RestauranteApenasNomeModel> listarApenasNome();
	
	@ApiOperation("Busca um restaurante por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID de restaurante inválido"
					, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado"
			, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public RestauranteModel buscar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);
	

	@ApiOperation("Cadastra um novo restautante")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Restaurante cadastrado")
	})
	public RestauranteModel adicionar(
			@ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true) RestauranteInput restauranteInput);
	
	
	@ApiOperation("Atualiza um restaurante por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado"
		, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public RestauranteModel atualizar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true) RestauranteInput restauranteInput);
	
	@ApiOperation("Ativa um restaurante por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Restaurante ativado"),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado"
		, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<Void> ativar (
			@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);
	
	
	@ApiOperation("Inativa um restaurante por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Restaurante inativado"),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado"
		, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<Void> inativar (
			@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);
	
	
	@ApiOperation("Ativa multiplos restaurantes")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso")
	})
	public void ativarMultiplos(
			@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true) List<Long> restauranteIds);
	
	
	@ApiOperation("Inativa multiplos restaurantes")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Restaurantes inativados")
	})
	public void inativarMultiplos(
			@ApiParam(value = "IDs de restaurantes", name = "corpo", required = true) List<Long> restauranteIds);
	

	@ApiOperation("Abrir um restaurante por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso"),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado"
		, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<Void> abrirRestaurante(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);
	
	@ApiOperation("Fechar um restaurante por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Restaurante fechado com sucesso"),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado"
		, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<Void> fecharRestaurante(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);
}
