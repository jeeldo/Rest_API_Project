package com.algaworks.algafood.domain.exception;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FotoProdutoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public FotoProdutoNaoEncontradaException(Long produtoId, Long restauranteId) {
		this(String.format("Não existe cadastro de foto de procuto com código %d para o restaurant de código %d", produtoId, restauranteId));
	}	
}
