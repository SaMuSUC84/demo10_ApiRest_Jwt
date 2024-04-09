package es.dsw.controller.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/user")
public class UsuarioController 
{

	@Autowired
	private AuthenticationService authenticationService;
	
	
	@GetMapping(value= {"/validate"})
	public ResponseEntity<Boolean> validar(
			@RequestParam String jwt)
	{
		boolean isTokenValid = authenticationService.validateToken(jwt);
		return ResponseEntity.ok(isTokenValid);
	} 
	
	
	@PostMapping(value = {"/authenticate"}, produces = "application/json")
	public ResponseEntity<AuthenticationResponse> autenticar(
			@RequestBody AuthenticationRequest authRequest)
	{
		AuthenticationResponse rsp = authenticationService.login(authRequest);
		
		return ResponseEntity.ok(rsp);
	}

}
