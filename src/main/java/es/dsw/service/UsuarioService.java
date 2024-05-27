package es.dsw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import es.dsw.models.util.Role;


@Service
public class UsuarioService 
{	
	/*
	 *  Creamos nuestro servicio de Usuarios.
	 *  JWT está pensado para actuar con ORM y BBDD embebida.
	 *  Nosotros vamos hacer uso del objeto InMemory de Spring para
	 *  cargar nuestros propios usuarios a modo de práctica y comprobar
	 *  el funcionamiento y configuración de JWT.
	 *  Es por ello que creamos nuestros usuarios como servicio y no como modelos o dto's.
	 */
	private InMemoryUserDetailsManager InMemory;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	/*
	 *  Creamos nuestro constructor de Usuarios
	 */
    public UsuarioService() 
    {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.InMemory = new InMemoryUserDetailsManager();
        inicializarUsuarios();
    }
    
    /*
     *  Método privado para crear nuestros Usuarios y precargarlos en memoria.
     */
    private void inicializarUsuarios() 
    {
    	UserDetails user1 = User.builder()
    			.username("samu")
    			.password(passwordEncoder.encode("1234"))
    			//Los roles son creados como Enums y nos interesa sacar su nombre de ahi el .name().
    			.roles(Role.ADMIN.name()) 
    			.build();
    	
    	UserDetails user2 = User.builder()
    			.username("dani")
    			.password(passwordEncoder.encode("4567"))
    			.roles(Role.ADMIN.name())
    			.build();

        InMemory.createUser(user1);
        InMemory.createUser(user2);
    }
    
    /*
     *  Método para encontrar un usuario por sus Username, haciendo
     *  uso del método loadUserByUsername del objeto InMemory.
     *  Este método es necesario para acceder al usuario logeado en nuestro
     *  método login de nuestro servicio AuthenticationService y por el cual 
     *  crearemos nuestro JWT Token con sus credenciales.
     */
    public UserDetails findByUsername(String username) 
    {
        return InMemory.loadUserByUsername(username);
    }
}


