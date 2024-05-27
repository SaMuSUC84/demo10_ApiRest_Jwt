package es.dsw.service.auth;

import java.sql.Date;
import java.util.Map;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService 
{	
	/*
	 *  Nuestro servicio para crear el token JWT que viajara en nuestra
	 *  cabecera Http.
	 *  Por buena praxis y seguridad creamos nuestra clave secreta en el properties y
	 *  con la anotacion @Value hacemos referencia al valor de esa propiedad.
	 *  La clave secreta se pasa por un codificador BASE.64 la cual está con ese valor
	 *  guarada en el fichero mencionado.
	 *  Lo mismo ocurre con el valor del tiempo de expiración del token.
	 */
	
	@Value("${security.jwt.secret-key}")
	private String SECRET_KEY;
	
	
	@Value("${security.jwt.expiration-in-minutes}")
	private Long EXPIRATION_IN_MINUTES;
	
	
	/*
	 *  Método para generar el Token. Recibe por parametros el usuario
	 *  y un Map que son los Claims del token.
	 */
	public String generarToken(UserDetails objUser, 
							   Map<String, Object> extraClaims ) 
	{
		/*
		 *  Hacemos un print del Header a modo de debug de nuestro properties.
		 */
		System.out.println("Mi clave Secreta: " + SECRET_KEY);
		System.out.println("Mi expiration time: " + EXPIRATION_IN_MINUTES);	
		
		/*
		 *  Creamos las variables de tiempo tanto de creación (issuedAt) como expiración (expiration).
		 *  Todo está calculado en milisegundos.
		 */
		Date issuedAt = new Date(System.currentTimeMillis());
		Date expiration = new Date((EXPIRATION_IN_MINUTES * 60 * 1000) + issuedAt.getTime()); 
		
		/*
		 *  Creamo el string con el cuepro del JWT.
		 */
		String jwt = Jwts.builder()
				/*
				 * HEADER del JWT
				 */
				.header()
					.type("JWT")
					.and()	
				/*
				 * PAYLOAD del JWT
				 */
				// propietario
				.subject(objUser.getUsername()) 
				// Tiempo de creación
				.issuedAt(issuedAt)
				// Tiempo de expiración
				.expiration(expiration) 
				// Claims o información afirmada del token
				.claims(extraClaims) 
				// La firma del token con la clave secreta y el algoritmo HS256 (estandar más utilizado).
				.signWith(generatedkey(),Jwts.SIG.HS256) 
				// Por ultimo lo creamos con el método compact().
				.compact();	
		
		/*
		 *  Hacemos un print del Header a modo de debug.
		 */
		System.out.println("Mi JWT: " + jwt );
		return jwt;	
	}
	
	/*
	 *  Métdodo privado para decodificar de BASE.64 nuestra SECRET_KEY
	 *  y pasarla decodificada al metodo .signWith de la creación 
	 *  del PAYLOAD del JWT.
	 */
	private SecretKey generatedkey() 
	{
		byte [] passwordDecoded = Decoders.BASE64.decode(SECRET_KEY);
		/*
		 *  Hacemos un print en consola para comprobar que se decodifica correctamente.
		 */
		System.out.println(new String(passwordDecoded));
		return Keys.hmacShaKeyFor(passwordDecoded);
	}
	
	/*
	 *  Métdodo que nos devuelve el propietario del token.
	 */
	public String extractUsername (String jwt) 
	{
		return extractAllClaims(jwt).getSubject();		
	}
	
	
	/*
	 *  Método que nos devuelve los Claims del JWT, haciendo uso
	 *  de la clase utileria Jwts la cual ha sido importada en el Maven
	 *  del proyecto. 
	 */
	private Claims extractAllClaims(String jwt) 
	{
		return Jwts.parser().verifyWith(generatedkey()).build()
				.parseSignedClaims(jwt).getPayload();
	}
}
