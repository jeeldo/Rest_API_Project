package com.algaworks.algafood.api.v1.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.CidadeController;
import com.algaworks.algafood.api.v1.controller.EstadoController;
import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel>{
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	public CidadeModelAssembler() {
		super(CidadeController.class, CidadeModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CidadeModel toModel (Cidade cidade) {
		CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);
		modelMapper.map(cidade, cidadeModel);
		
		if(algaSecurity.podeConsultarCidades()) {
			cidadeModel.add(algaLinks.linkToCidades("cidades"));	
		}	
		
//		cidadeModel.getEstado().add(linkTo(methodOn(EstadoController.class).buscar(cidadeModel.getEstado().getId())).withSelfRel());
		if(algaSecurity.podeConsultarEstados()) {
			cidadeModel.getEstado().add(algaLinks.linkToEstado(cidadeModel.getEstado().getId()));
		}
		return cidadeModel;
		
//		CidadeModel cidadeModel = modelMapper.map(cidade, CidadeModel.class);
//		cidadeModel.add(linkTo(methodOn(CidadeController.class).buscar(cidadeModel.getId())).withSelfRel());	
	}
	
	@Override
	public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
		CollectionModel<CidadeModel> collectionModel =  super.toCollectionModel(entities);

		if(algaSecurity.podeConsultarCidades()) {
			collectionModel.add(algaLinks.linkToCidades());
		}
		
		return collectionModel;
	}
	
//	public List<CidadeModel> toCollectionModel (List<Cidade> cidades) {
//		return cidades.stream()
//				.map(cidade -> toModel(cidade))
//				.collect(Collectors.toList());
//	}
}
