package it.inail.ingress.gateway.poc.pratica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {
		"it.inail.*" 
		})public class PraticaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PraticaApplication.class, args);
	}

}
