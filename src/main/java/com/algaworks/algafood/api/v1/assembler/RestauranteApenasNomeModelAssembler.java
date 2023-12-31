package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteApenasNomeModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeModel>{

	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public RestauranteApenasNomeModelAssembler() {
		super(RestauranteController.class, RestauranteApenasNomeModel.class);
	}
	
	public RestauranteApenasNomeModel toModel(Restaurante restaurante) {
		RestauranteApenasNomeModel restauranteApenasNomeModel = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteApenasNomeModel);
		restauranteApenasNomeModel.add(algaLinks.linkToRestaurantes("restaurantes"));
		return restauranteApenasNomeModel;
	}
	
	@Override
	public CollectionModel<RestauranteApenasNomeModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToRestaurantes());
	}
}
