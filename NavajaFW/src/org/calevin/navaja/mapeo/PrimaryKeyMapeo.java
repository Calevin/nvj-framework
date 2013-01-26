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

}
