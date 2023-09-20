package com.algaworks.algafood.api.v1.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel>{
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public RestauranteModelAssembler() {
		super(RestauranteController.class, RestauranteModel.class);
	}
	
	public RestauranteModel toModel(Restaurante restaurante) {
		RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteModel);
		
		if(restaurante.getEndereco() != null && restaurante.getEndereco().getCidade() != null) {
			restauranteModel.getEndereco().getCidade().add(algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
		}
		restauranteModel.add(algaLinks.linkToProdutos(restauranteModel.getId(), "produtos"));
		restauranteModel.getCozinha().add(algaLinks.linkToCozinha(restauranteModel.getCozinha().getId()));
		restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
		restauranteModel.add(algaLinks.linkToRestauranteFormaPagamento(restauranteModel.getId(), "formas-pagamento"));
		restauranteModel.add(algaLinks.linkToRestauranteUsuarioResponsavel(restauranteModel.getId(), "responsáveis"));
		if(restaurante.podeSerAberto()) {
			restauranteModel.add(algaLinks.linkToAbrirRestaurante(restaurante.getId(), "abrir-restaurante"));
		}
		if(restaurante.podeSerFechado()) {
			restauranteModel.add(algaLinks.linkToFecharRestaurante(restaurante.getId(), "fechar-restaurante"));
		}
		if(restaurante.podeSerAtivado()) {
			restauranteModel.add(algaLinks.linkToAtivarRestaurante(restaurante.getId(), "ativar-restaurante"));
		}
		if(restaurante.podeSerInativado()) {
			restauranteModel.add(algaLinks.linkToInativarRestaurante(restaurante.getId(), "inativar-restaurante"));
		}
		return restauranteModel;
	}
	
	@Override
	public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToRestaurantes());

	}

}
