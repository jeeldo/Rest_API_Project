package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("RestaurantesBasicoModel")
public class RestaurantesBasicoModelOpenApi {

	private RestaurantesResumoEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@Data
	@ApiModel("RestaurantesBasicoEmbeddedModel")
	public class RestaurantesResumoEmbeddedModelOpenApi{
		List<RestauranteBasicoModel> restaurantes;
	}
}
