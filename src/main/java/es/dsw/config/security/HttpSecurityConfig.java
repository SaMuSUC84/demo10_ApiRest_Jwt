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
	/*
	 * Configuración de nuestro Http, donde pasamos nuestros filtros
	 * y nuestra lógica a la hora de hacer nuestras peticiones.
	 * Inyectamos nuestras dependencias y una de ellas en especial 
	 * nuestro filtro JWT llamado JwtAuthenticationFilter.
	 */
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		return http
				/*
				 *  Desactivamos la protección CSRF (Cross-Site Request Forgery) 
				 * debido a que nosotros manejaremos nuestro propio token JWT.
				 */
				.csrf((csrfConfig) -> csrfConfig.disable())
				/*
				 *  Establecemos la política de gestión de sesiones a STATELESS, 
				 * lo que significa que el servidor no creará ni gestionará sesiones.
				 */
				.sessionManagement((sessionMagConfig) -> sessionMagConfig
				   									     .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				 )
				// Establecemos el proveedor de autenticación.
				.authenticationProvider(authenticationProvider)
				/*
				 * Añadimos los filtros en indicamos nuestro filtro personalizado JWT 
				 * antes de uno propio que proporciona Spring Security.
				 */
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				/*
				 *  Configuramos la autorización para las solicitudes HTTP de los endpoints.
				 */
				.authorizeHttpRequests((authorize) -> authorize
													  .requestMatchers(HttpMethod.POST,"/auth/authenticate").permitAll()
													  .requestMatchers(HttpMethod.GET,"/auth/validate-token").permitAll()
										              .anyRequest().authenticated()
				 )	
				/*
				 *  Al final construimos y devolvemos el SecurityFilterChain.
				 */
				.build();
	}

}
