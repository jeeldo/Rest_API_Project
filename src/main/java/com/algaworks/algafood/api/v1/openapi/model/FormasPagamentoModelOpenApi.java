package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("FormasPagamentoModel")
public class FormasPagamentoModelOpenApi {

	private FormasPagamentoEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@Data
	@ApiModel("FormasPagamentoEmbeddedModel")
	public class FormasPagamentoEmbeddedModelOpenApi{
		
		private List<FormaPagamentoModel> formasPagamento;
	}
}
