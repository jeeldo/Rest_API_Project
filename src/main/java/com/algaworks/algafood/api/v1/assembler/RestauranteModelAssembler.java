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
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel>{
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	public RestauranteModelAssembler() {
		super(RestauranteController.class, RestauranteModel.class);
	}
	
	public RestauranteModel toModel(Restaurante restaurante) {
		RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteModel);
		
		if(algaSecurity.podeConsultarCidades()) {
			if(restaurante.getEndereco() != null && restaurante.getEndereco().getCidade() != null) {
				restauranteModel.getEndereco().getCidade().add(algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
			}
		}
		
		if(algaSecurity.podeConsultarRestaurantes()) {
			restauranteModel.add(algaLinks.linkToProdutos(restauranteModel.getId(), "produtos"));
		}
		
		if(algaSecurity.podeConsultarCozinhas()) {
			restauranteModel.getCozinha().add(algaLinks.linkToCozinha(restauranteModel.getCozinha().getId()));
		}
		
		if(algaSecurity.podeConsultarRestaurantes()) {
			restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
		}

		if(algaSecurity.podeConsultarFormasPagamento()) {
			restauranteModel.add(algaLinks.linkToRestauranteFormaPagamento(restauranteModel.getId(), "formas-pagamento"));
		}
		
		if(algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			restauranteModel.add(algaLinks.linkToRestauranteUsuarioResponsavel(restauranteModel.getId(), "responsáveis"));
		}
		
		if(algaSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
			if(restaurante.podeSerAberto()) {
				restauranteModel.add(algaLinks.linkToAbrirRestaurante(restaurante.getId(), "abrir-restaurante"));
			}
			if(restaurante.podeSerFechado()) {
				restauranteModel.add(algaLinks.linkToFecharRestaurante(restaurante.getId(), "fechar-restaurante"));
			}
		}
		
		if(algaSecurity.podeGerenciarCadastroRestaurantes()) {
			if(restaurante.podeSerAtivado()) {
				restauranteModel.add(algaLinks.linkToAtivarRestaurante(restaurante.getId(), "ativar-restaurante"));
			}
			if(restaurante.podeSerInativado()) {
				restauranteModel.add(algaLinks.linkToInativarRestaurante(restaurante.getId(), "inativar-restaurante"));
			}	
		}
		
		return restauranteModel;
	}
	
	@Override
	public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		CollectionModel<RestauranteModel> collectionModel = super.toCollectionModel(entities);
				
		if(algaSecurity.podeConsultarRestaurantes()) {
			collectionModel.add(algaLinks.linkToRestaurantes());
		}	

		return collectionModel;
	}

}
