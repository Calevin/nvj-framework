package org.calevin.navaja.mapeo;

/**
 * Clase que representa el Campo de una tabla del mapeo
 * 
 * @author calevin
 */
public class CampoMapeo {

	private String nombre;
	
	private String nombreComoAtributo;
	
	public CampoMapeo() {
		super();
	}

	public CampoMapeo(String nombre, String nombreComoAtributo) {
		super();
		this.nombre = nombre;
		this.nombreComoAtributo = nombreComoAtributo;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreComoAtributo() {
		return nombreComoAtributo;
	}

	public void setNombreComoAtributo(String nombreComoAtributo) {
		this.nombreComoAtributo = nombreComoAtributo;
	}

}
