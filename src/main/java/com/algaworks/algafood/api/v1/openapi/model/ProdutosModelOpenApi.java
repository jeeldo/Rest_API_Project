package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.ProdutoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("ProdutosModel")
public class ProdutosModelOpenApi {

	private ProdutosEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@Data
	@ApiModel("ProdutosEmbeddedModel")
	public class ProdutosEmbeddedModelOpenApi{
		
		List<ProdutoModel> produtos;
	}
}
