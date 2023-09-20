package com.algaworks.algafood.api.v1.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.api.v1.model.CidadeResumoModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {
	
	@ApiModelProperty(example = "99070000", required = true)
	@NotBlank
	private String cep;
	
	@ApiModelProperty(example = "Avenida Presidente Vargas", required = true)
	@NotBlank
	private String logradouro;	
	
	@ApiModelProperty(example = "\"519\"", required = true)
	@NotBlank
	private String numero;	
	
	@ApiModelProperty(example = "Sala 01", required = false)
	private String complemento;
	
	@ApiModelProperty(example = "Petropolis", required = true)
	@NotBlank
	private String bairro;
	
	@Valid
	@NotNull
	private CidadeIdInput cidade;
	
}
