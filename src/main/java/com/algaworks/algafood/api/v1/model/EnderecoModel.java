package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModel {

	@ApiModelProperty(example = "99070-000")
	private String cep;
	
	@ApiModelProperty(example = "Avenida Presidente Vargas")
	private String logradouro;	
	
	@ApiModelProperty(example = "519")
	private String numero;	
	
	@ApiModelProperty(example = "Sala 01")
	private String complemento;
	
	@ApiModelProperty(example = "São Cristovão")
	private String bairro;
	
	private CidadeResumoModel cidade;
}
