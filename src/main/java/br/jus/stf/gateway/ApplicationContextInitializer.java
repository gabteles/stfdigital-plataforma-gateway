package br.jus.stf.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import io.undertow.servlet.api.DeploymentInfo;

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
	
	/**
	 * Este permite customizar o Undertow para permitir o forwarding de erros na resposta de requisição
	 * feita ao gateway
	 */
	@Bean
    public UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory() {
        UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();
        factory.addDeploymentInfoCustomizers(new UndertowDeploymentInfoCustomizer() {
            @Override
            public void customize(DeploymentInfo deploymentInfo) {
                deploymentInfo.setAllowNonStandardWrappers(true);
            }
        });
        return factory;
    }
	
	/**
	 * A <a href="http://www.w3.org/Security/wiki/Same_Origin_Policy">Política de Mesma Origem</a>
	 * é um importante conceito de segurança implementado pelos navegadores para evitar que códigos 
	 * Javascript façam requições para um domínio diferente do seu domínio de origem. 
	 * 
	 * Cross-Origin Resource Sharing (CORS) é a técnica usada para flexibilizar a aplicação da
	 * política para permitir que um código Javascript em uma página possa consumir a API REST 
	 * de um outro domínio.
	 */
	@Bean
	public CorsFilter corsFilter() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    final CorsConfiguration configuration = new CorsConfiguration();
	    /**
	     * Todos as origens são permitidas.
	     */
	    configuration.addAllowedOrigin(CorsConfiguration.ALL);
	    /**
	     * Apenas os headers abaixo são suportados. Uma requisição com um header fora dessa lista será rejeitada.
	     */
	    configuration.addAllowedHeader("Content-Type");
	    configuration.addAllowedHeader("Accept");
	    /**
	     * Todos os métodos abaixo são suportados. Uma requisição com método fora dessa lista será rejeitada.
	     */
	    configuration.addAllowedMethod(CorsConfiguration.ALL);
	    source.registerCorsConfiguration("/**", configuration);
	    return new CorsFilter(source);
	}
	
}
