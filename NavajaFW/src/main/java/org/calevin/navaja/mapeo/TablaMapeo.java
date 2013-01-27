package org.calevin.navaja.mapeo;

import java.util.ArrayList;
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
	* Retorna el CampoMepeo de la tabla correspondiente a ese nombre segun lo
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
    
}
