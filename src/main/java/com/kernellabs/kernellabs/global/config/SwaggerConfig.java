package com.kernellabs.kernellabs.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI(){
		Server server = new Server();
		server.setDescription("Production Server");

		return new OpenAPI()
			.components(new Components())
			.info(apiInfo())
			.addServersItem(server);
	}

	private Info apiInfo(){
		return new Info()
			.title("Improfessor API")
			.description("Improfessor API Documentation")
			.version("1.0");
	}
}