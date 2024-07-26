package com.algaworks.algafood.core.springfox;


import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.PermissaoModel;
import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.openapi.model.CidadesModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.CozinhasModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.EstadosModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.FormasPagamentoModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.GruposModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.LinksModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PageableModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PedidosResumoModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PermissoesModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.ProdutosModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.RestaurantesBasicoModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.UsuariosModelOpenApi;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.CozinhaModelV2;
import com.algaworks.algafood.api.v2.openapi.model.CidadesModelOpenApiV2;
import com.algaworks.algafood.api.v2.openapi.model.CozinhasModelOpenApiV2;
import com.algaworks.algafood.api.v2.openapi.model.LinksModelOpenApiV2;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.Response;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer{

	@Bean
	public JacksonModuleRegistrar springFoxJacksonConfig() {
		return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
	}
	
	private Consumer<RepresentationBuilder> getProblemaModelReference(){
		return r -> r.model(m -> m.name("Problema")
				.referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
						q -> q.name("Problema").namespace("com.algaworks.algafood.api.exceptionhandler")))));
	}
	
	@Bean
	public Docket apiDocketV1() {
		
		var typeResolver = new TypeResolver();
		return new Docket(DocumentationType.SWAGGER_2)//DocumentationType.OAS_30
				.groupName("V1")
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
					.paths(PathSelectors.ant("/v1/**"))
//					.paths(PathSelectors.any())
//					.paths(PathSelectors.ant("/restaurantes/*"))
					.build()
				.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
//				.globalRequestParameters(Collections.singletonList(
//			            new RequestParameterBuilder()
//			                    .name("campos")
//			                    .description("Nomes das propriedades para filtrar na resposta, separados por vírgula")
//			                    .in(ParameterType.QUERY)
//			                    .required(true)
//			                    .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//			                    .build()))
				.additionalModels(typeResolver.resolve(Problem.class))
				.ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class, Resource.class
						, InputStream.class, File.class)
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.directModelSubstitute(Links.class, LinksModelOpenApi.class)
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, CozinhaModel.class)
						, CozinhasModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, PedidoResumoModel.class)
						, PedidosResumoModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, CidadeModel.class)
						, CidadesModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, EstadoModel.class)
						, EstadosModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, FormaPagamentoModel.class)
						, FormasPagamentoModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, GrupoModel.class)
						, GruposModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, PermissaoModel.class)
						, PermissoesModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, ProdutoModel.class)
						, ProdutosModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, RestauranteBasicoModel.class)
						, RestaurantesBasicoModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, UsuarioModel.class)
						, UsuariosModelOpenApi.class))
				
				.securitySchemes(Arrays.asList(securityScheme()))
				.securityContexts(Arrays.asList(securityContext()))
				
				.apiInfo(apiInfoV1())
				.tags(new Tag("Cidades", "Gerencia as Cidades"),
						new Tag("Grupos", "Gerencia os Grupos"),
						new Tag("Cozinhas", "Gerencia as Cozinhas"),
						new Tag("Formas de Pagamento", "Gerencia as Formas de Pagamento"),
						new Tag("Pedidos", "Gerencia os Pedidos"),
						new Tag("Restaurantes", "Gerencia os Restaurantes"), 
						new Tag("Estados", "Gerencia os Estados"), 
						new Tag("Produtos", "Gerencias os Produtos"),
						new Tag("Usuarios", "Gerencia os Usuarios"),
						new Tag("Estatisticas", "Gerencias as Estatisticas"),
						new Tag("Permissoes", "Gerencia as Permissoes")
						);
	}
	
	
	private SecurityScheme securityScheme() {
		return new OAuthBuilder()
			.name("AlgaFood")
			.grantTypes(grantTypes())
			.scopes(scopes())
			.build();
	}
	
	private SecurityContext securityContext() {
		
		var securityReference = SecurityReference.builder()
				.reference("AlgaFood")
				.scopes(scopes().toArray(new AuthorizationScope[0]))
				.build();
		
		return SecurityContext.builder()
				.securityReferences(Arrays.asList(securityReference))
				.forPaths(PathSelectors.any())
				.build();
	}
	
	private List<GrantType> grantTypes(){
		return Arrays.asList(new springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant("/oauth/token"));
	}
	
	private List<AuthorizationScope> scopes() {
		return Arrays.asList(new AuthorizationScope("READ", "Acesso de Leitura"),
				new AuthorizationScope("WRITE", "Acesso de Escrita"));
	}
	
	@Bean
	public Docket apiDocketV2() {
		
		var typeResolver = new TypeResolver();
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("V2")
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
					.paths(PathSelectors.ant("/v2/**"))

					.build()
				.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())

				.additionalModels(typeResolver.resolve(Problem.class))
				.ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class, Resource.class
						, InputStream.class, File.class)
				.directModelSubstitute(Links.class, LinksModelOpenApiV2.class)
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, CidadeModelV2.class)
						, CidadesModelOpenApiV2.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
					    typeResolver.resolve(PagedModel.class, CozinhaModelV2.class),
					    CozinhasModelOpenApiV2.class))
				
				.securitySchemes(Arrays.asList(securityScheme()))
				.securityContexts(Arrays.asList(securityContext()))
				
				.apiInfo(apiInfoV2())
				.tags(
					new Tag("Cidades", "Gerencia as cidades"),
					new Tag("Cozinhas", "Gerencia as cozinhas")
					);
		
	}
	
	private List<Response> globalDeleteResponseMessages() {
		return Arrays.asList(
				new ResponseBuilder()
					.code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
					.description("Requisição inválida (erro do cliente)")
					.representation(MediaType.APPLICATION_JSON)
					.apply(getProblemaModelReference())
					.build(),
				new ResponseBuilder()
					.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
					.description("Erro interno do servidor")
					.representation(MediaType.APPLICATION_JSON)
					.apply(getProblemaModelReference())
					.build()
				);
	}

	private List<Response> globalPostPutResponseMessages() {
		return Arrays.asList(
				new ResponseBuilder()
					.code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
					.description("Requisição inválida (erro do cliente)")
					.representation(MediaType.APPLICATION_JSON)
					.apply(getProblemaModelReference())
					.build(),
				new ResponseBuilder()
					.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
					.description("Erro interno do servidor")
					.representation(MediaType.APPLICATION_JSON)
					.apply(getProblemaModelReference())
					.build(),
				new ResponseBuilder()
					.code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
					.description("Recurso não possui representação que poderia ser aceita pelo consumidor")
					.build(),
				new ResponseBuilder()
					.code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
					.description("Requisição recusado porque o corpo está em um formato não aceitável")
					.representation(MediaType.APPLICATION_JSON)
					.apply(getProblemaModelReference())
					.build()
				);
	}

	private List<Response> globalGetResponseMessages(){
		return Arrays.asList(
				new ResponseBuilder()
					.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
					.description("Erro interno do servidor")
					.representation(MediaType.APPLICATION_JSON)
					.apply(getProblemaModelReference())
					.build(),
				new ResponseBuilder()
					.code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
					.description("Recurso não possui representação que poderia ser aceita pelo consumidor")
					.build()
				);
	}
	
	private ApiInfo apiInfoV1() {
		return new ApiInfoBuilder()
				.title("AlgaFood API (Depreciada)")
				.description("API aberta para clientes e restaurantes.<br>"
						+ "<strong>Essa versão da API está depreciada e deixará de exitir em 10/04/2024.<br>"
						+ "Use a versão mais atual da API.")
				.version("1")
				.contact(new Contact("Jean Mello", "https://algaworks.com", "jeanrmsevero@gmail.com"))
				.build();
	}
	
	private ApiInfo apiInfoV2() {
		return new ApiInfoBuilder()
				.title("AlgaFood API")
				.description("API aberta para clientes e restaurantes")
				.version("2")
				.contact(new Contact("Jean Mello", "https://algaworks.com", "jeanrmsevero@gmail.com"))
				.build();
	}
	
	/*
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui/index.html")
			.addResourceLocations("classpath:/META-INF/resources/");
		
		registry.addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	*/
}
