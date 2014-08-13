package org.calevin.navaja.mapeo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Clase que reprensenta una Tabla del Mapeo
 * @author calevin
 */
public class TablaMapeo {

    private PrimaryKeyMapeo primaryKeyMapeo;
    private ArrayList<CampoMapeo> campos = new ArrayList<CampoMapeo>();
    private String nombre;
    private String nombreComoClase;



    /**
	* Retorna el CampoMapeo de la tabla correspondiente a ese nombre segun lo
    * mapeado en los archivos de configuracion
	* @param nombreCampo del campo a retornar
	* @return el Campo correspondiete a ese nombre
	*/
    public CampoMapeo getCampoMapeoPorNombre(String nombreCampo){
    	
    	for (Iterator<CampoMapeo> iterator = this.campos.iterator(); iterator.hasNext();) {
			CampoMapeo tmp = (CampoMapeo) iterator.next();
			if (tmp.getNombre().equals(nombreCampo) ){
				return tmp;
			}
		}
    	return null;
    }

    /**
     * Retorna el CampoMapeo de la tabla correspondiente a ese nombre de atributo segun lo
     * mapeado en los archivos de configuracion
     * @param nombreAtributo del cual se buscara su correspondiente columna
     * @return el Campo correspondiente a ese atributo
     */
    public CampoMapeo getCampoMapeoPorAtributo(String nombreAtributo) {
    	
    	for (Iterator<CampoMapeo> iterator = this.campos.iterator(); iterator.hasNext();) {
			CampoMapeo tmp = (CampoMapeo) iterator.next();
			if (tmp.getNombreComoAtributo().equals(nombreAtributo) ){
				return tmp;
			}
		}
    	return null;
    }    
    
    //SETTERS Y GETTERS
    public PrimaryKeyMapeo getPrimaryKeyMapeo() {
		return primaryKeyMapeo;
	}
	public void setPrimaryKeyMapeo(PrimaryKeyMapeo primaryKeyMapeo) {
		this.primaryKeyMapeo = primaryKeyMapeo;
	}
	public ArrayList<CampoMapeo> getCampos() {
		return campos;
	}
	public void setCampos(ArrayList<CampoMapeo> campos) {
		this.campos = campos;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombreComoClase() {
		return nombreComoClase;
	}
	public void setNombreComoClase(String nombreComoClase) {
		this.nombreComoClase = nombreComoClase;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((campos == null) ? 0 : campos.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((nombreComoClase == null) ? 0 : nombreComoClase.hashCode());
		result = prime * result
				+ ((primaryKeyMapeo == null) ? 0 : primaryKeyMapeo.hashCode());
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
		TablaMapeo other = (TablaMapeo) obj;
		if (campos == null) {
			if (other.campos != null)
				return false;
		}
		
		if (campos != null & other.campos != null){
			Collections.sort(campos, new CampoMapeo());
			Collections.sort(other.campos, new CampoMapeo());			
		}
		
		if (!campos.equals(other.campos))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (nombreComoClase == null) {
			if (other.nombreComoClase != null)
				return false;
		} else if (!nombreComoClase.equals(other.nombreComoClase))
			return false;
		if (primaryKeyMapeo == null) {
			if (other.primaryKeyMapeo != null)
				return false;
		} else if (!primaryKeyMapeo.equals(other.primaryKeyMapeo))
			return false;
		return true;
	}

}
