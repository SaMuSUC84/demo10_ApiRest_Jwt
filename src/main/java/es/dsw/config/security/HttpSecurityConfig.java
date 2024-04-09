package es.dsw.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import es.dsw.config.security.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig 
{		
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		return http
				.csrf((csrfConfig) -> csrfConfig.disable())
				.sessionManagement((sessionMagConfig) -> sessionMagConfig
				   									     .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				 )
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.authorizeHttpRequests((authorize) -> authorize
										    	      .requestMatchers(HttpMethod.POST, "/user/authenticate").permitAll()
										    	      .requestMatchers(HttpMethod.POST, "/user/validate").permitAll()
										              .anyRequest().authenticated()
				 )	
				.build();
	}

}
