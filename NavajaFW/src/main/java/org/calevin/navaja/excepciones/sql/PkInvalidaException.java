package org.calevin.navaja.excepciones.sql;

import org.calevin.navaja.util.NavajaConstantes;

public class PkInvalidaException extends RuntimeException{

	private static final long serialVersionUID = 6639356603139979929L;

    private static final String PK_INVALIDA = "PK invalida";
    
    public PkInvalidaException(){
    	super(PK_INVALIDA 
    			+ NavajaConstantes.COMA_ESPACIO
    			+ NavajaConstantes.ES
    			+ NavajaConstantes.ESPACIO
    			+ NavajaConstantes.NULA);
    }
    
    public PkInvalidaException(String... campos){
    	super(PK_INVALIDA
    			+ NavajaConstantes.COMA_ESPACIO
    			+ getCamposNulosMensaje(campos));    			
    }
    
    private static String getCamposNulosMensaje(String... campos){
    	String camposParaMensaje = "";
    	for (int i = 0; i < campos.length; i++) {
    		if (i != 0){
    			camposParaMensaje += NavajaConstantes.COMA_ESPACIO;
    		}
    		camposParaMensaje += NavajaConstantes.CAMPO;
    		camposParaMensaje += NavajaConstantes.ESPACIO_COMILLA;
    		camposParaMensaje += campos[i];
    		camposParaMensaje += NavajaConstantes.COMILLA_ESPACIO;
    		camposParaMensaje += NavajaConstantes.ES;
    		camposParaMensaje += NavajaConstantes.ESPACIO;
    		camposParaMensaje += NavajaConstantes.NULO;
    	}
    	return camposParaMensaje;
    }
}
