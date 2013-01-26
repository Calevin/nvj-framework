package org.calevin.navaja.util;

public class NavajaStringUtil {

    /**
     * Conmuta del String recibido el caracter de la posicion indicada, entre
     * mayuscula y minuscula, o viceversa segun corresponda
     *
     * @param string String a conmutar
     * @param posicion Posicion del caracter a conmutar
     * @return
     */	
    public static String conmutarCaseChar(String string, int posicion) {
        // Ej. NavajaStringUtil.conmutarCaseChar("Pablo",0) => "pablo"
        // Ej. NavajaStringUtil.conmutarCaseChar("Pablo",2) => "PaBlo"
        // Ej. NavajaStringUtil.conmutarCaseChar("Pablo",325) => NULL    	
        String aRetornar = null;
        if (!(posicion < 0 || posicion >= string.length())){
        	aRetornar = "";
	        //Se recorre el String
	        for (int i = 0; i < string.length(); i++) {
	            //Si el la posicion indicada
	            if (i == posicion) {
	                //Si es minuscula
	                if (Character.isLowerCase(string.charAt(posicion))) {
	                    //Se convierte a mayuscula y se lo agrega al nuevo string
	                    aRetornar += string.toUpperCase().charAt(posicion);
	                } else {
	                    //Si es mayuscula, se lo convierte en minuscula
	                	// y se lo agrega al nuevo string
	                    aRetornar += string.toLowerCase().charAt(posicion);
	                }
	                //Si no es la posicion indicada se continua copiando el String
	            } else {
	                aRetornar += string.charAt(i);
	            }
	        }//fin for
        } //fin else
        return aRetornar;
    }
}
