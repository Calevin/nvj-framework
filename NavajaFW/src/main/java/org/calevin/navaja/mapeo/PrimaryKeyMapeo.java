package org.calevin.navaja.mapeo;

import java.util.ArrayList;

/**
 * Clase que representa una PK del mapeo
 * 
 * @author calevin
 */
public class PrimaryKeyMapeo {

	private ArrayList<CampoMapeo> campos = new ArrayList<CampoMapeo>();

	public ArrayList<CampoMapeo> getCampos() {
		return campos;
	}

	public void setCampos(ArrayList<CampoMapeo> campos) {
		this.campos = campos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((campos == null) ? 0 : campos.hashCode());
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
		PrimaryKeyMapeo other = (PrimaryKeyMapeo) obj;
		if (campos == null) {
			if (other.campos != null)
				return false;
		} else if (!campos.equals(other.campos))
			return false;
		return true;
	}

}
