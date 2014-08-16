package org.calevin.navaja.excepciones.bean;

import org.calevin.navaja.util.NavajaConstantes;
import org.calevin.navaja.util.NavajaStringUtil;

public class BeanInvocarGetterException extends BeanException {

	private static final long serialVersionUID = -8981597463074541865L;

	public BeanInvocarGetterException() {
		super(NavajaStringUtil.conmutarCaseChar(NavajaConstantes.ERROR, 0)
        		+ NavajaConstantes.ESPACIO
        		+ NavajaConstantes.AL_INVOCAR_METODO
        		+ NavajaConstantes.ESPACIO            		
        		+ NavajaConstantes.GET
        		+ NavajaConstantes.ESPACIO        		
        		+ NavajaConstantes.NO_DEFINIDO       		
        		+ NavajaConstantes.PUNTO);
	}
	
	public BeanInvocarGetterException(String atributo, Exception e) {
		super(NavajaStringUtil.conmutarCaseChar(NavajaConstantes.ERROR, 0)
        		+ NavajaConstantes.ESPACIO
        		+ NavajaConstantes.AL_INVOCAR_METODO
        		+ NavajaConstantes.ESPACIO            		
        		+ NavajaConstantes.GET
        		+ NavajaStringUtil.conmutarCaseChar(atributo, 0)
        		+ NavajaConstantes.PUNTO
        		+ NavajaConstantes.ESPACIO        		
        		+ e); 
	}
	
}
