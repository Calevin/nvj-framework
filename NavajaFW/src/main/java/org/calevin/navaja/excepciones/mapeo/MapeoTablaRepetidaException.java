package org.calevin.navaja.excepciones.mapeo;

import org.calevin.navaja.util.NavajaConstantes;
import org.calevin.navaja.util.NavajaStringUtil;

public class MapeoTablaRepetidaException extends MapeoException {

	private static final long serialVersionUID = -5031799116864633483L;

	public MapeoTablaRepetidaException(String nombreTabla) {

        super(NavajaStringUtil.conmutarCaseChar(NavajaConstantes.TABLA, 0)
                + NavajaConstantes.ESPACIO_COMILLA 
                + nombreTabla
                + NavajaConstantes.COMILLA_ESPACIO
                + NavajaConstantes.DEFINIDA_DOS_VECES
                + NavajaConstantes.PUNTO);
    }
}
