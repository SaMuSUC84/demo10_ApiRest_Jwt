package es.dsw.controller.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.dsw.models.auth.AuthenticationRequest;
import es.dsw.models.auth.AuthenticationResponse;

import es.dsw.service.auth.AuthenticationService;


@RestController
@RequestMapping("/auth")
public class AuthenticationController 
{
	@Autowired
	private AuthenticationService authenticationService;
	
	/*
	 *  Endpoint de utileria para comprobar si el token es válido.
	 */
	@GetMapping(value= {"/validate-token"})
	public ResponseEntity<Boolean> validar(
			@RequestParam String jwt)
	{
		boolean isTokenValid = authenticationService.validateToken(jwt);
		return ResponseEntity.ok(isTokenValid);
	} 
	
	/*
	 *  Endpoint de autenticación.
	 */
	@PostMapping(value = {"/authenticate"}, produces = "application/json")
	public ResponseEntity<AuthenticationResponse> autenticar(
			@RequestBody AuthenticationRequest authRequest)
	{
		AuthenticationResponse rsp = authenticationService.login(authRequest);
		
		return ResponseEntity.ok(rsp);
	}

}
