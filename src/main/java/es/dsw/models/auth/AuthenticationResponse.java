package es.dsw.models.auth;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AuthenticationResponse implements Serializable
{
	/*
	 * Clase para manejar la response del usuario
	 */
	
	private String jwt;

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
}
