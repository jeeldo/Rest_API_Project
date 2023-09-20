package com.algaworks.algafood.api.exceptionhandler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@ApiModel("ObjetoProblema")
@Builder
@Getter
@Setter
public class Field {

	@ApiModelProperty(example = "preço")
	private String name;
	
	@ApiModelProperty(example = "o preço é obrigatório")
	private String userMessage;

	
	public Field(String name, String userMessage) {
		super();
		this.name = name;
		this.userMessage = userMessage;
	}
	
	
}
