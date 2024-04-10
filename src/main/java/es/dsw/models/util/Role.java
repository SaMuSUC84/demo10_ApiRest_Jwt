package es.dsw.models.util;

import java.util.Arrays;
import java.util.List;

public enum Role 
{	
	ADMIN(Arrays.asList(
			RolePermiso.READ_ALL,
			RolePermiso.READ_MY_PROFILE
	)),
	
	USER(Arrays.asList(
			RolePermiso.READ_MY_PROFILE
	));
	
	
	private List<RolePermiso> permisos;

	Role(List<RolePermiso> permisos) {
		this.permisos = permisos;
	}

	public List<RolePermiso> getPermisos() {
		return permisos;
	}

	public void setPermisos(List<RolePermiso> permisos) {
		this.permisos = permisos;
	}	
}
