package com.algaworks.algafood.api.v1.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FotoProduto;

@Component
public class FotoProdutoModelAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModel>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	public FotoProdutoModelAssembler() {
		super(RestauranteProdutoFotoController.class, FotoProdutoModel.class);
	}
	
	public FotoProdutoModel toModel (FotoProduto fotoProduto) {
		FotoProdutoModel fotoProdutoModel = modelMapper.map(fotoProduto, FotoProdutoModel.class);
		
		// Quem pode consultar restaurantes, também pode consultar os produtos e fotos
		if(algaSecurity.podeConsultarRestaurantes()) {
			fotoProdutoModel.add(algaLinks.linkToProduto(fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId()));
			fotoProdutoModel.add(algaLinks.linkToProduto(fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId(), "produto"));
		}
		return fotoProdutoModel;
	}
	
	@Override
	public CollectionModel<FotoProdutoModel> toCollectionModel(Iterable<? extends FotoProduto> entities) {
		return super.toCollectionModel(entities);
	}

}
