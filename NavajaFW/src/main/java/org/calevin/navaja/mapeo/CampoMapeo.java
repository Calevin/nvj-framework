package org.calevin.navaja.mapeo;

import java.util.Comparator;

/**
 * Clase que representa el Campo de una tabla del mapeo
 * 
 * @author calevin
 */
public class CampoMapeo implements Comparator<CampoMapeo>{

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime
				* result
				+ ((nombreComoAtributo == null) ? 0 : nombreComoAtributo
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CampoMapeo other = (CampoMapeo) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (nombreComoAtributo == null) {
			if (other.nombreComoAtributo != null)
				return false;
		} else if (!nombreComoAtributo.equals(other.nombreComoAtributo))
			return false;
		return true;
	}

	@Override
	public int compare(CampoMapeo o1, CampoMapeo o2) {
	 return ((o1.getNombre()).compareTo(o2.getNombre()));
	}

}
