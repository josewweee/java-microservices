package co.edu.eafit.bank.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String ACTUATOR_BASE                = "/actuator";
	private static final String MATCHERS_ACTUATOR_HEALTH     = ACTUATOR_BASE + "/health";
	private static final String MATCHERS_ACTUATOR_PROMETHEUS = ACTUATOR_BASE + "/prometheus";
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			
				.antMatchers(MATCHERS_ACTUATOR_HEALTH).permitAll()
		        .antMatchers(MATCHERS_ACTUATOR_PROMETHEUS).permitAll()
				
				//Todo request -> Autenticado
				.anyRequest().authenticated()
			
				//Todo request tener el rol
				//.anyRequest().access("authenticated AND hasRole('bank_holder')")
			.and()
				.oauth2ResourceServer()
					.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()));
	}

	private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
		JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
		jwtConverter.setJwtGrantedAuthoritiesConverter(new RealmRoleConverter());
		return jwtConverter;
	}
}
