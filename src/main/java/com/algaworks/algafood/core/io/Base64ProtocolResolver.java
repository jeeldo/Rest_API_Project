package com.algaworks.algafood.core.io;

import java.util.Base64;

import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class Base64ProtocolResolver implements ProtocolResolver, 
		ApplicationContextInitializer<ConfigurableApplicationContext>{

	@Override
	public Resource resolve(String location, ResourceLoader resourceLoader) {
		if(location.startsWith("base64:")) {
			byte[] decodedResource = Base64.getDecoder().decode(location.substring(7));
			return new ByteArrayResource(decodedResource);
		}
		return null;
	}

	@Override
	public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
		configurableApplicationContext.addProtocolResolver(this);
		
	}


}
