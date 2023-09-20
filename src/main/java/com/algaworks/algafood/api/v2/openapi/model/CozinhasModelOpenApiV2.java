package com.algaworks.algafood.api.v2.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v2.model.CozinhaModelV2;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("CozinhasModel")
public class CozinhasModelOpenApiV2 {

	private CozinhasEmbeddedModelOpenApiV2 _embedded;
	private Links _links;
	private PageModelOpenApiV2 page; 
	
	@Getter
	@Setter
	@ApiModel("CozinhasEmbeddedModel")
	public class CozinhasEmbeddedModelOpenApiV2{
		private List<CozinhaModelV2> cozinhas;
	}
}
