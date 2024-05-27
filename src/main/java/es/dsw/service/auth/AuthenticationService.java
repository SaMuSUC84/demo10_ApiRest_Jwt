package es.dsw.service.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import es.dsw.models.auth.AuthenticationRequest;
import es.dsw.models.auth.AuthenticationResponse;
import es.dsw.service.UsuarioService;

@Service
public class AuthenticationService
{
	/*
	 *  Nuestor servicio de autenticación, tiene que ser anotado con
	 *  la java anotation de @Service para indicarle a Spring que es 
	 *  un servicio. En este servicio es donde aplicaremos nuestra lógica
	 *  de login a la cual tendremos que inyectar dependencias con @Autowired
	 *  de nuestro servicio JWT y Usuarios.
	 */
	@Autowired
	private UsuarioService objUsuarioService;
	
	@Autowired
	private JwtService jwtService;	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	/*
	 *  Método privado para generar nuestros Claims del JWT.
	 */
	private Map<String, Object> generateExtraClaims(UserDetails objUser)
	{
		Map<String, Object> extraClaims = new HashMap<>();
		
		extraClaims.put("name", objUser.getUsername());	
		extraClaims.put("authorities", objUser.getAuthorities());
		return extraClaims;		
	}
	

	/*
	 * Método del servicio de Authentication para el login.
	 * Para ello creamos dos clases dto en el package Models en un 
	 * subpackcage Autgh para las autenticación.
	 * Los dtos creados son AuthenticationResponse y AuthenticactionRequest.
	 */
	public AuthenticationResponse login(AuthenticationRequest authRequest) 
	{
		Authentication auth = new UsernamePasswordAuthenticationToken(
				authRequest.getUsername(), authRequest.getPassword()
		);
		
		authenticationManager.authenticate(auth);
		
		UserDetails user = objUsuarioService.findByUsername(authRequest.getUsername());
		
		String jwt = jwtService.generarToken(user, generateExtraClaims(user));
		
		AuthenticationResponse authResp = new AuthenticationResponse();
		authResp.setJwt(jwt);
		
		return authResp;
	}
	
	/*
	 *  Método de utileria público para validar un token JWT.
	 */
	public boolean validateToken(String jwt) 
	{
		try {
			jwtService.extractUsername(jwt);
			return true;
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}	
	}

}
