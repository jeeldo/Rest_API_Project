package com.algaworks.algafood.api.v1.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.CidadeController;
import com.algaworks.algafood.api.v1.controller.FormaPagamentoController;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.v1.controller.UsuarioController;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel>{

	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public PedidoModel toModel (Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoModel);
		
//		pedidoModel.add(linkTo(PedidoController.class).withRel("pedidos"));
		
	    // Não usei o método algaSecurity.podePesquisarPedidos(clienteId, restauranteId) aqui,
	    // porque na geração do link, não temos o id do cliente e do restaurante, 
	    // então precisamos saber apenas se a requisição está autenticada e tem o escopo de leitura
		if(algaSecurity.podePesquisarPedidos()) {
			pedidoModel.add(algaLinks.linkToPedidos("pedidos"));
		}
		
		if(algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			pedidoModel.getCliente().add(algaLinks.linkToUsuario(pedidoModel.getCliente().getId()));
		}
		
		if(algaSecurity.podeConsultarRestaurantes()) {
			pedidoModel.getRestaurante().add(algaLinks.linkToRestaurante(pedidoModel.getRestaurante().getId()));
		}
		
		
		if(algaSecurity.podeConsultarFormasPagamento()) {
			pedidoModel.getFormaPagamento().add(algaLinks.linkToFormaPagamento(pedidoModel.getFormaPagamento().getId()));
		}
		
		if(algaSecurity.podeConsultarCidades()) {
			pedidoModel.getEnderecoEntrega().getCidade().add(algaLinks.linkToCidade(pedidoModel.getEnderecoEntrega().getCidade().getId()));
		}
		
		// Quem pode consultar restaurantes, também pode consultar os produtos dos restaurantes
		if(algaSecurity.podeConsultarRestaurantes()) {
			pedidoModel.getItens().forEach(item -> 
				item.add(algaLinks.linkToProduto(pedidoModel.getRestaurante().getId(), item.getId(), "produto")));
		}
		
		if(algaSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
			
			if(pedido.podeSerConfirmado()) {
				pedidoModel.add(algaLinks.linkToConfirmacaoPedido(pedidoModel.getCodigo(), "confirmar"));
			}
			
			if(pedido.podeSerCancelado()) {
				pedidoModel.add(algaLinks.linkToCancelamentoPedido(pedidoModel.getCodigo(), "cancelar"));
			}
			
			if(pedido.podeSerEntregue()) {
				pedidoModel.add(algaLinks.linkToEntregaPedido(pedidoModel.getCodigo(), "entregar"));
			}
		}
		
		return pedidoModel;
	}
	
	@Override
	public CollectionModel<PedidoModel> toCollectionModel(Iterable<? extends Pedido> entities) {
		return super.toCollectionModel(entities);
	}
	
	
	/*
	public List<PedidoModel> toCollectionModel (List<Pedido> pedidos) {
		return pedidos.stream()
				.map(pedido -> toModel(pedido))
				.collect(Collectors.toList());
	}
	*/
}
