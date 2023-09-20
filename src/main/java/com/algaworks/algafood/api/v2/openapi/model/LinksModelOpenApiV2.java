package com.algaworks.algafood.api.v2.openapi.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("Links")
@Getter
@Setter
public class LinksModelOpenApiV2 {

	private LinkModelV2 rel;
	
	@ApiModel("Link")
	@Getter
	@Setter
	public class LinkModelV2{
		
		private String href;
		private boolean templated;
	}
}
