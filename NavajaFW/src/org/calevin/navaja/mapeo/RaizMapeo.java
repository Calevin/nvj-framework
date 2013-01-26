package org.calevin.navaja.mapeo;

import java.util.ArrayList;

import org.calevin.navaja.excepciones.mapeo.ClaseNoExisteEnElMapeoExcepcion;

/**
 * Clase que reprensenta el elemento raiz del xml Mapeo OR
 * @author calevin
 */
public class RaizMapeo {
	
    private ArrayList<TablaMapeo> tablas = new ArrayList<TablaMapeo>();
    private ArrayList<String> nombresClase = new ArrayList<String>();

    /**
     * Retorna la TablaMapeo a partir del nombre de la clase
     * @param nombreClase nombreClase nombre de la clase de la cual se retorna
     * su correspondiente TablaMapeo
     * @return TablaMapeo correspondiente a ese nombre de clase en el mapeo
     */
    public TablaMapeo getTablaPorNombreClase(String nombreClase) throws ClaseNoExisteEnElMapeoExcepcion {
        //Se iteran las tablas cargadas
        for (TablaMapeo tabla : this.tablas) {
            //Si nombre indicado coincide con una clase se retorna su correspondiente tabla
            if (tabla.getNombreComoClase().equals(nombreClase)) {
                //Se retorna
                return tabla;
            } else {
                throw new org.calevin.navaja.excepciones.mapeo.ClaseNoExisteEnElMapeoExcepcion(nombreClase);
            }
        }

        return null;
    }    
    
    
    public ArrayList<TablaMapeo> getTablas() {
		return tablas;
	}
	public void setTablas(ArrayList<TablaMapeo> tablas) {
		this.tablas = tablas;
	}
	public ArrayList<String> getNombresClase() {
		return nombresClase;
	}
	public void setNombresClase(ArrayList<String> nombresClase) {
		this.nombresClase = nombresClase;
	}

    public void setNombreClase(String nombreClase) {
        this.nombresClase.add(nombreClase);
    }
}
