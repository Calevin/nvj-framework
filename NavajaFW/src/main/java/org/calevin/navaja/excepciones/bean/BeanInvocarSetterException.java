package org.calevin.navaja.excepciones.bean;

import org.calevin.navaja.util.NavajaConstantes;
import org.calevin.navaja.util.NavajaStringUtil;

public class BeanInvocarSetterException extends BeanException {

	private static final long serialVersionUID = -7313492611709605581L;

	public BeanInvocarSetterException(String mensaje){
		super(NavajaStringUtil.conmutarCaseChar(NavajaConstantes.ERROR, 0)
        		+ NavajaConstantes.ESPACIO
        		+ NavajaConstantes.AL_INVOCAR_METODO
        		+ NavajaConstantes.ESPACIO            		
        		+ NavajaConstantes.SET
        		+ NavajaConstantes.PUNTO_ESPACIO
        		+ mensaje);
	}
	
	public BeanInvocarSetterException() {
		super(NavajaStringUtil.conmutarCaseChar(NavajaConstantes.ERROR, 0)
        		+ NavajaConstantes.ESPACIO
        		+ NavajaConstantes.AL_INVOCAR_METODO
        		+ NavajaConstantes.ESPACIO            		
        		+ NavajaConstantes.SET
        		+ NavajaConstantes.ESPACIO        		
        		+ NavajaConstantes.NO_DEFINIDO       		
        		+ NavajaConstantes.PUNTO);
	}
	
	public BeanInvocarSetterException(String atributo, Object valor, Exception e) {
		super(NavajaStringUtil.conmutarCaseChar(NavajaConstantes.ERROR, 0)
        		+ NavajaConstantes.ESPACIO
        		+ NavajaConstantes.AL_INVOCAR_METODO
        		+ NavajaConstantes.ESPACIO            		
        		+ NavajaConstantes.SET
        		+ NavajaStringUtil.conmutarCaseChar(atributo, 0)
        		+ NavajaConstantes.ESPACIO
        		+ NavajaConstantes.CON
        		+ NavajaConstantes.ESPACIO
        		+ NavajaConstantes.VALOR
        		+ NavajaConstantes.ESPACIO_COMILLA
        		+ valor
        		+ NavajaConstantes.COMILLA        		
        		+ NavajaConstantes.PUNTO
        		+ NavajaConstantes.ESPACIO        		
        		, e); 
	}	
}
