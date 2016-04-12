package br.jus.stf.gateway.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * A <a href="http://www.w3.org/Security/wiki/Same_Origin_Policy">Política de Mesma Origem</a>
 * é um importante conceito de segurança implementado pelos navegadores para evitar que códigos 
 * Javascript façam requições para um domínio diferente do seu domínio de origem. 
 * 
 * Cross-Origin Resource Sharing (CORS) é a técnica usada para flexibilizar a aplicação da
 * política para permitir que um código Javascript em uma página possa consumir a API REST 
 * de um outro domínio.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 22.07.2015
 */
@Component
public class CorsFilter extends OncePerRequestFilter {
	
    /**
     * Indica quais os domínios de origem do cliente são permitidas para requisições ao servidor deste filtro.
     */
    public static final String ORIGINS = "Access-Control-Allow-Origin";

    /**
     * Indica quais métodos o cliente poderá usar para fazer a requisição
     */
    public static final String METHODS = "Access-Control-Allow-Methods";

    /**
     * Indica quais headers o cliente poderá enviar como parte da requisição
     */
    public static final String HEADERS = "Access-Control-Allow-Headers";
    
    /**
     * Todos as origens são permitidas.
     */
    public static final String ALLOWED_ORIGINS = "*";

    /**
     * Todos os métodos abaixo são suportados. Uma requisição com método fora dessa lista será rejeitada.
     */
    public static final String ALLOWED_METHODS = "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH";
    
    /**
     * Apenas os headers abaixo são suportados. Uma requisição com um header fora dessa lista será rejeitada.
     */
    public static final String ALLOWED_HEADERS = "Content-Type, Accept, papel";
    
	/**
	 * Para implementar a técnica descrita acima, precisamos incluir Headers CORS no response.
	 * O filtro implementado aqui intercepta todas as requisições e adiciona os headers em todas as
	 * respostas, garantindo acesso por clientes de outros domínios.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		
		// Indica quais headers o cliente pode adicionar à sua requisição.
		response.setHeader(HEADERS, ALLOWED_HEADERS);
		
		// Indica quais métodos o cliente poderá usar
		response.setHeader(METHODS, ALLOWED_METHODS);
		
		// Indica quais os domínios de origem permitidos para o cliente
		response.setHeader(ORIGINS, ALLOWED_ORIGINS);
		
		// Por fim, apenas repassa a requisição
		chain.doFilter(request, response);
		
	}

}