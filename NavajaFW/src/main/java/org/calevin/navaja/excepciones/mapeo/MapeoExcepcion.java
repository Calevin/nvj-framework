package org.calevin.navaja.excepciones.mapeo;

import org.calevin.navaja.util.NavajaConstantes;
import org.xml.sax.SAXException;

public class MapeoExcepcion extends SAXException {


	private static final long serialVersionUID = 2656937898212969223L;
	
	private static final String EXCEPCION_AL_MAPEAR = "Excepcion al mapear";

    public MapeoExcepcion() {
    super(EXCEPCION_AL_MAPEAR + NavajaConstantes.PUNTO);     
    }
    
    public MapeoExcepcion(String message) {
        super(EXCEPCION_AL_MAPEAR 
                + NavajaConstantes.PUNTO 
                + NavajaConstantes.ESPACIO 
                + message);
    }
}
