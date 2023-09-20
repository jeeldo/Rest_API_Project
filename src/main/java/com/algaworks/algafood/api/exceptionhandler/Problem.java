package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
//import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Builder
@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
public class Problem {
	
	@ApiModelProperty(example = "400", position = 1) 
	private Integer status;
	
	@ApiModelProperty(example = "https://algaworks.com.br/dados-invalidos", position = 2)
	private String type;
	
	@ApiModelProperty(example = "Dados inválidos", position = 3)
	private String title;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.", position = 4)
	private String detail;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.", position = 5)
	private String userMessage;
	
	@ApiModelProperty(example = "2023-01-14T15:51:19.4637061Z", position = 6)
	private OffsetDateTime timestamp;
	
	@ApiModelProperty(value = "Lista de Objetos ou campos que geraram o erro (opcional)", position = 7)
	private List<Field> fields;

}
