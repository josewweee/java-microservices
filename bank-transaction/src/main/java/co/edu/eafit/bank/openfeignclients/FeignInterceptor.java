package co.edu.eafit.bank.openfeignclients;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FeignInterceptor implements RequestInterceptor{

	@Override
	public void apply(RequestTemplate template) {
		String url = template.url();
		
		log.info("Reading and setting headers to " + url);
		
		JwtAuthenticationToken token = (JwtAuthenticationToken)SecurityContextHolder.getContext()
				.getAuthentication();
		
		if (token!=null && token.getToken()!=null) {

			log.info("Escribiendo el Bearer token en la petición a: " + url);
			
			//Setting headers
			template.header("Authorization", "Bearer " + token.getToken().getTokenValue());
		}
		
		//Reading headers
		Map<String, Collection<String>> headers = template.headers();
		headers.forEach((key, values) -> {
			log.info("Key: " + key);
			values.forEach(value -> log.info("Value: " + value));
			
		});
	}
}