package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.model.input.SenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Usuarios")
public interface UsuarioControllerOpenApi {
	
	@ApiOperation("Lista todos usuários")
	public CollectionModel<UsuarioModel> listar();
	
	
	@ApiOperation("Busca um usuário por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do usuário inválido"
					, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Usuário não encontrado"
			, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public UsuarioModel buscar(
			@ApiParam(value = "ID de um usuário", example = "1", required = true) Long usuarioId);
	

	@ApiOperation("Adiciona um novo usuário")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Novo usuário cadastrado com sucesso")
	})
	public UsuarioModel adicionar(
			@ApiParam(name = "corpo", value = "Representação de um novo usuário com senha", required = true) UsuarioComSenhaInput usuarioInput);
	
	@ApiOperation("Atualiza os dados de um usuário")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
		@ApiResponse(responseCode = "404", description = "Usuário não encontrado"
		, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public UsuarioModel atualizar(
			@ApiParam(value = "ID de um usuário", example = "1", required = true) Long usuarioId, 
			@ApiParam(name = "corpo", value = "Novos dados de usuário", required = true) UsuarioInput usuarioInput);
	
	@ApiOperation("Altera a senha de um usuário")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
		@ApiResponse(responseCode = "404", description = "Usuário não encontrado"
		, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public void alterarSenha(
			@ApiParam(value = "ID de um usuário", example = "1", required = true) Long usuarioId, 
			@ApiParam(name = "corpo", value = "Representaçao com senha atual e novav", required = true) SenhaInput senhaInput);

}
