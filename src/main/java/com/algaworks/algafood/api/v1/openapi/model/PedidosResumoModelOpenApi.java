package com.algaworks.algafood.api.v1.openapi.model;
import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.PedidoResumoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("PedidosResumoModel")
public class PedidosResumoModelOpenApi {

	private PedidosResumoEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PageModelOpenApi page;
	
	@Data
	@ApiModel("PedidosResumoEmbeddedModel")
	public class PedidosResumoEmbeddedModelOpenApi{
		
		private List<PedidoResumoModel> pedidos;
	}
}
