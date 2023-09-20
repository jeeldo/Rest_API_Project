package com.algaworks.algafood.api.v1.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.v1.model.EstatisticaModel;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Estatisticas")
public interface EstatisticasControllerOpenApi {

	@ApiOperation(value = "Estatisticas", hidden = true)
	EstatisticaModel estatisticas();
	
	@ApiOperation("Consulta as vendas diárias")
	@ApiImplicitParams({
		@ApiImplicitParam(example = "2022-11-01T03:00:00Z", value = "Data/hora final da criação do pedido"
				, name = "dataCriacaoFim", dataType = "date-time", paramType = "query"),
		@ApiImplicitParam(example = "2022-11-01T03:00:00Z", value = "Data/hora inicial da criação do pedido"
		, name = "dataCriacaoInicio", dataType = "date-time", paramType = "query"),
		@ApiImplicitParam(example = "1", value = "ID de um restaurante"
		, name = "restauranteId", dataType = "int")
	})
	public List<VendaDiaria> consultarVendasDiarias(
			VendaDiariaFilter filtro, 
			@ApiParam(value = "Deslocamento de horário a ser considerado na consulta, em relação ao UTC", defaultValue = "+00:00") String timeOffset);
	
	
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(
			VendaDiariaFilter filtro, 
			String timeOffset);
}
