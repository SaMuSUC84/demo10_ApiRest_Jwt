package es.dsw.controller.apis;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.dsw.models.Alumno;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController 
{	
	private ArrayList<Alumno> objResultado = new ArrayList<Alumno>();
	
	@GetMapping(value = "/getAll", produces="application/json") 
	public ResponseEntity<?> getAll()
	{		
		objResultado.add(new Alumno("55665544A", "Alfredo", "Martín Pérez", 34, true));
		objResultado.add(new Alumno("55665543B", "Carlos", "Gutierrez Bueno", 22, true));
		objResultado.add(new Alumno("55665542B", "Susana", "Hernandez Martín", 45, true));
		objResultado.add(new Alumno("55665541C", "Beatriz", "Bueno Pérez", 50, false));
		objResultado.add(new Alumno("55665549C", "Alejandro", "Sanchez Montesdeoca", 38, true));
		objResultado.add(new Alumno("55662544D", "Abian", "Pérez Pérez", 76, false));
		objResultado.add(new Alumno("55664544E", "Gustavo", "Ortega Dorta", 28, true));
		objResultado.add(new Alumno("55665564F", "Hector", "Dorta Pérez", 30, true));
		objResultado.add(new Alumno("55665554G", "Begoña", "Betancort Hernandez", 31, true));
		objResultado.add(new Alumno("15665144H", "Sara", "Martín Pérez", 39, true));
		objResultado.add(new Alumno("25665544T", "Kilian", "Bueno Jimenez", 42, false));
		objResultado.add(new Alumno("35665544N", "Kevin", "Montesdeoca Betancort", 40, true));
		
		return new ResponseEntity<>(objResultado, HttpStatus.OK); 
	}
	
	
	@PostMapping(value = "/getOne", produces="application/json")
	public ResponseEntity<?>  getOne(@RequestParam(name="nif", defaultValue="") String nif) 
	{
		Alumno alumnoEncontrado = new Alumno();
	    
		if (nif.trim().equals("")) 
	    {
	        return new ResponseEntity<>("NIF no proporcionado", HttpStatus.NOT_FOUND);
	    }
		
		alumnoEncontrado.setDni(nif);
		alumnoEncontrado.setNombre("Alfredo");
		alumnoEncontrado.setApellidos("Martín Pérez");
		alumnoEncontrado.setEdad(34);
		alumnoEncontrado.setPromociona(true);	
		
	    return new ResponseEntity<>(alumnoEncontrado, HttpStatus.OK);    
	}
	
		
	
	
    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> agregarAlumno(@RequestBody Alumno alumno)
    {
    	return new ResponseEntity<>("Alumno recibido correctamente", HttpStatus.CREATED);
    }

}
