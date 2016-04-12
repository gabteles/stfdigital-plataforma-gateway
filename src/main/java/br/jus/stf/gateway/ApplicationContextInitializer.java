package br.jus.stf.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 06.04.2016
 */
@SpringBootApplication
@EnableZuulProxy
public class ApplicationContextInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationContextInitializer.class, args);
	}
	
}
