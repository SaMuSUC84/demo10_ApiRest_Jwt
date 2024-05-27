package es.dsw.config.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import es.dsw.service.UsuarioService;
import es.dsw.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter 
{	
	/* Creamos nuestro filtro personalizado de JWT para pasarlo en la 
	 * configuraciuon de HttpSecurity en la propiedad .addFilterBefore().
	 * Heredamos de OncePerRequestFilter a que nos aseguramos de que nuestro filtro 
	 * se invoque solo una vez por solicitud.
	 * Tenemos qe sobrescribir su método doFilterInternal().
	 * Para las solicitudes asíncronas, OncePerRequestFilter no se aplica por defecto y 
	 * necesitariamos sobrescribir los métodos shouldNotFilterAsyncDispatch() y shouldNotFilterErrorDispatch().
	 */
	@Autowired
	private JwtService jwtService;

	@Autowired	
	private UsuarioService objUsuarioService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain filterChain
									) throws ServletException, IOException 
	{
		System.out.println("ENTRO EN EL FILTRO JWT AUTHENTICATION");
		
		/*
		 *  Esta linea imprime todos los encabezados para comprobar
		 *  que en nuestra petición se envia correctamente el 
		 *  encabezado de Authorization relacionado con JWT.
		 *  Descomentala para probar e importa la clase Enumeration de Java Util.	 
		 */ 	
/*
	    Enumeration<String> headerNames = request.getHeaderNames();
	    while (headerNames.hasMoreElements()) {
	        String headerName = headerNames.nextElement();
	        System.out.println("Header Name: " + headerName + ", Value: " + request.getHeader(headerName));
	    }
*/
		
		// 1. Obtener el encabezado http llamado Authorization. 
		String authHeader = request.getHeader("Authorization");
		// Hacemos un print del Header a modo de debug.
		System.out.println(authHeader);
		
		// condicionar con el método StringUtils de Spring el contexto de la variable
		// para que si viene vacia o su encabezado no empieza por Bearer continue con los filtros.
		if(!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) 
		{
			filterChain.doFilter(request, response);
			return;
		}
		
		// 2. Obtener el token JWT desde el encabezado. 
		String jwt = authHeader.split(" ")[1];		
		
		// 3. Obtener el subject/username desde el token, que a su vez
		//	  está acción valida eñ formato del token, firma y fecha de expiración.
		String username = jwtService.extractUsername(jwt);
		
		// 4. Setter al objeto authentication dentro del Security Context Holder.
		UserDetails userDetails = objUsuarioService.findByUsername(username);	
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				username, null, userDetails.getAuthorities()
		);
		SecurityContextHolder.getContext().setAuthentication(authToken);		
		
		// 5. Ejecutar el registro de filtros. 
		filterChain.doFilter(request, response);
	}
}
