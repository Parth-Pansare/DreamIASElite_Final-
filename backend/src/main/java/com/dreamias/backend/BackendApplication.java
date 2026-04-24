package com.dreamias.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Dream IAS Backend application.
 * 
 * @SpringBootApplication is a convenience annotation that adds all of the following:
 * - @Configuration: Tags the class as a source of bean definitions for the application context.
 * - @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings.
 * - @ComponentScan: Tells Spring to look for other components, configurations, and services in the package.
 */
@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@org.springframework.context.event.EventListener
	public void handleContextRefresh(org.springframework.context.event.ContextRefreshedEvent event) {
		org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping mapping = 
			event.getApplicationContext().getBean(org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping.class);
		mapping.getHandlerMethods().keySet().forEach(info -> 
			System.out.println("DEBUG MAPPING: " + info)
		);
	}
}
