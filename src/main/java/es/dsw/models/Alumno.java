package es.dsw.models;

public class Alumno {

	private String Dni;
	private String Nombre;
	private String Apellidos;
	private int Edad;
	private boolean Promociona;
	
	public Alumno(String dni, String nombre, String apellidos, int edad, boolean promociona) {
		Dni = dni;
		Nombre = nombre;
		Apellidos = apellidos;
		Edad = edad;
		Promociona = promociona;
	}
	
	public String getDni() {
		return Dni;
	}
	public void setDni(String dni) {
		Dni = dni;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getApellidos() {
		return Apellidos;
	}
	public void setApellidos(String apellidos) {
		Apellidos = apellidos;
	}
	public int getEdad() {
		return Edad;
	}
	public void setEdad(int edad) {
		Edad = edad;
	}
	public boolean isPromociona() {
		return Promociona;
	}
	public void setPromociona(boolean promociona) {
		Promociona = promociona;
	}
	
	
}
