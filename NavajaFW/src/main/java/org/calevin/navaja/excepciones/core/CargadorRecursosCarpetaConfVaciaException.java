package org.calevin.navaja.excepciones.core;

import org.calevin.navaja.util.NavajaConstantes;
import org.calevin.navaja.util.NavajaStringUtil;

public class CargadorRecursosCarpetaConfVaciaException extends
		CargadorRecursosException {

	private static final long serialVersionUID = -3461809183637427245L;

	public CargadorRecursosCarpetaConfVaciaException(String carpetaArchivosConf) {
        super(NavajaStringUtil.conmutarCaseChar(NavajaConstantes.CARPETA_DE_CONF, 0)
                + NavajaConstantes.ESPACIO_COMILLA
                + carpetaArchivosConf
                + NavajaConstantes.COMILLA_ESPACIO
                + NavajaConstantes.VACIA_O_NULA
                + NavajaConstantes.PUNTO);
	}
}
