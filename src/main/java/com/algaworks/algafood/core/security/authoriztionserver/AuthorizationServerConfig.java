package com.algaworks.algafood.core.security.authoriztionserver;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.proc.JWKSecurityContext;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
//	@Autowired
//	private RedisConnectionFactory redisConnectionFactory;
	
	@Autowired
	private JwtKeyStoreProperties jwtKeyStoreProperties;
	
	@Autowired
	private DataSource dataSource;
	
	/*
	 * CADASTRO DE CLIENTES EM BANCO DE DADOS
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);
	}
	
	/*
	 * CADASTRO DE CLIENTES EM MEMÓRIA
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
			.inMemory()
				.withClient("algafood-web")
				.secret(passwordEncoder.encode("web123"))
				.authorizedGrantTypes("password", "refresh_token")
				.scopes("WRITE", "READ")
				.accessTokenValiditySeconds(60 * 60 * 6)//6 horas
				.refreshTokenValiditySeconds(60 * 24 * 60 * 60)//60 dias
				
			.and()
				.withClient("algafood-web")
				.secret(passwordEncoder.encode("web123"))
				.authorizedGrantTypes("password", "refresh_token")
				.scopes("WRITE", "READ")
				.accessTokenValiditySeconds(60 * 60 * 6)//6 horas
				.refreshTokenValiditySeconds(60 * 24 * 60 * 60)//60 dias
				
			.and()
				.withClient("foodanalytics")
				.secret(passwordEncoder.encode("food123"))
				//.secret(passwordEncoder.encode(""))
				.authorizedGrantTypes("authorization_code")
				.scopes("WRITE", "READ")
				.redirectUris("http://www.foodanalytics.local:8082")
				
			.and()
				.withClient("webadmin")
				.authorizedGrantTypes("implicit")
				.scopes("WRITE", "READ")
				.redirectUris("http://aplicacao-cliente")
				
			.and()
				.withClient("checktoken")
				.secret(passwordEncoder.encode("check123"));
	}
	*/
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("permitAll()")
			.tokenKeyAccess("permitAll()")
			.allowFormAuthenticationForClients();
//		security.checkTokenAccess("isAuthenticated()");
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		var enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(
				Arrays.asList(new JwtCustomClaimsTokenEnhancer(), jwtAccessTokenConverter()));
		
		endpoints
			.authenticationManager(authenticationManager)
			.userDetailsService(userDetailsService)
			.authorizationCodeServices(new JdbcAuthorizationCodeServices(this.dataSource))
			.reuseRefreshTokens(false)
			.accessTokenConverter(jwtAccessTokenConverter())
			.tokenEnhancer(enhancerChain)
			.approvalStore(approvalStore(endpoints.getTokenStore()))
//			.tokenStore(redisTokenStore())
			.tokenGranter(tokenGranter(endpoints));
	}
	
	private ApprovalStore approvalStore(TokenStore tokenStore) {
		var approvalStore = new TokenApprovalStore();
		approvalStore.setTokenStore(tokenStore);
		
		return null;
	}
//	private TokenStore redisTokenStore() {
//		
//		return new RedisTokenStore(redisConnectionFactory);
//	}
	
	@Bean
	public JWKSet jwkSet() {
		RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) keyPair().getPublic())
				.keyUse(KeyUse.SIGNATURE)
				.algorithm(JWSAlgorithm.RS256)
				.keyID("algafood-key-id");
		
		return new JWKSet(builder.build());
	}
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		var jwtAccessTokenConverter = new JwtAccessTokenConverter();
		
//		jwtAccessTokenConverter.setSigningKey("huHUehiehiuehihiu3i43434h34hu3uh4uj34b3u4b");		
		jwtAccessTokenConverter.setKeyPair(keyPair());
		
		return jwtAccessTokenConverter;
	}
	
	private KeyPair keyPair() {
		
		var keyStorePass = jwtKeyStoreProperties.getPassword();
		var keyStoreAlias = jwtKeyStoreProperties.getKeypairAlias();		
		var keyStoreKeyFactory = new KeyStoreKeyFactory(jwtKeyStoreProperties.getJksLocation(), keyStorePass.toCharArray());
		return keyStoreKeyFactory.getKeyPair(keyStoreAlias);
		
	}
	
	private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
		var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
				endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
				endpoints.getOAuth2RequestFactory());
		
		var granters = Arrays.asList(
				pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());
		
		return new CompositeTokenGranter(granters);
	}
}
