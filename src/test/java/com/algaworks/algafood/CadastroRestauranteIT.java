package com.algaworks.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {

	@LocalServerPort	
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private Restaurante restauranteBeardBurger;
	
	private ResourceUtils resourceUtils;
	private int quantidadeRestauranteCadastrados;
	private static final int restauranteIdInexistente = 1000;
	private static final String excecaoDadosInvalidos = "Dados inválidos";
	private static final String excecaoViolacaoDeRegraDeNegocio = "Violação de regra de negócio";
	private String jsonRestauranteCorreto;
	private String jsonRestauranteSemCozinha;
	private String jsonRestauranteComCozinhaInexistente;
	private String jsonRestauranteSemTaxaFrete;
	private String jsonRestauranteSemNome;
	
	
	@BeforeAll
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/restaurantes";
		databaseCleaner.clearTables();
		prepararDados();
		
		jsonRestauranteCorreto = resourceUtils.getContentFromResource("/json/correto/restaurante-correto.json");
		jsonRestauranteComCozinhaInexistente = resourceUtils.getContentFromResource("/json/incorreto/restaurante-com-cozinha-inexistente.json");
		jsonRestauranteSemCozinha = resourceUtils.getContentFromResource("/json/incorreto/restaurante-sem-cozinha.json");
		jsonRestauranteSemTaxaFrete = resourceUtils.getContentFromResource("/json/incorreto/restaurante-sem-taxa-frete.json");
		jsonRestauranteSemNome = resourceUtils.getContentFromResource("/json/incorreto/restaurante-sem-nome.json");
	}
	
	/*
	 * 
	 * TESTES DE CONSULTAS
	 * 
	 * 
	 */
	
	@Test
	public void deveRetornar200_QuandoConsultarRestaurantes() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarQuantidadeCorretaDeRestaurantes_QuandoConsultarRestaurantes() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(quantidadeRestauranteCadastrados));
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarRestauranteExistente() {
		given()
			.pathParam("restauranteId", restauranteBeardBurger.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(restauranteBeardBurger.getNome()));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente() {
		given()
			.pathParam("restauranteId", restauranteIdInexistente)
			.accept(ContentType.JSON)
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	/*
	 * 
	 * TESTES DE CADASTROS
	 * 
	 * 
	 */
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarRestauranteComDadosCorretos() {
		given()
			.body(jsonRestauranteCorreto)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());	
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaInexistente() {
		given()
			.body(jsonRestauranteComCozinhaInexistente)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(excecaoViolacaoDeRegraDeNegocio));
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteSemCozinha() {
		given()
			.body(jsonRestauranteSemCozinha)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(excecaoDadosInvalidos));
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteSemTaxaFrete() {
		given()
			.body(jsonRestauranteSemTaxaFrete)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(excecaoDadosInvalidos));
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteSemNome() {
		given()
			.body(jsonRestauranteSemNome)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(excecaoDadosInvalidos));
	}
	
	private void prepararDados() {
		
		Cozinha cozinhaBrasileira = new Cozinha();
		cozinhaBrasileira.setNome("Brasileira");
		cozinhaRepository.save(cozinhaBrasileira);
		
		Cozinha cozinhaAmericana = new Cozinha();
		cozinhaAmericana.setNome("Americana");
		cozinhaRepository.save(cozinhaAmericana);
		
		Restaurante restauranteBarDoZe = new Restaurante();
		restauranteBarDoZe.setCozinha(cozinhaBrasileira);
		restauranteBarDoZe.setNome("Bar do Zé");
		restauranteBarDoZe.setTaxaFrete(new BigDecimal(12.00));
		restauranteRepository.save(restauranteBarDoZe);
		
		this.restauranteBeardBurger = new Restaurante();
		restauranteBeardBurger.setCozinha(cozinhaAmericana);
		restauranteBeardBurger.setNome("Beard Burger");
		restauranteBeardBurger.setTaxaFrete(new BigDecimal(8.00));
		restauranteRepository.save(restauranteBeardBurger);
		
		quantidadeRestauranteCadastrados = (int) restauranteRepository.count();

	}	
}
