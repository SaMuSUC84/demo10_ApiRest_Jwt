package es.dsw.models.auth;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AuthenticationResponse implements Serializable
{
	private String jwt;

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
}
