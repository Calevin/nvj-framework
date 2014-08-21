package org.calevin.navaja.util;

import junit.framework.Assert;

import org.calevin.navaja.util.NavajaStringUtil;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class NavajaStringUtilTest {

	private String STRING_LETRAS_MINUSCULAS = "string";
	private String STRING_PRIMERA_LETRA_MAYUSCULA = "String";	
	int primeraPosicionEnElString = 0;
	
	@Test
	public void conmutarCaseCharCasoCorrectoTest() {
		String rta = NavajaStringUtil.conmutarCaseChar(STRING_LETRAS_MINUSCULAS, primeraPosicionEnElString);
		
		Assert.assertTrue((rta.equals(STRING_PRIMERA_LETRA_MAYUSCULA)));

		rta = NavajaStringUtil.conmutarCaseChar(STRING_PRIMERA_LETRA_MAYUSCULA, primeraPosicionEnElString);
		
		Assert.assertTrue((rta.equals(STRING_LETRAS_MINUSCULAS)));		
	
	}
	
	@Test
	public void conmutarCaseCharRespuestaNulaTest() {
		String rta = NavajaStringUtil.conmutarCaseChar(STRING_LETRAS_MINUSCULAS, -1);
		
		Assert.assertNull(rta);
		
		rta = NavajaStringUtil.conmutarCaseChar(STRING_LETRAS_MINUSCULAS, 500);
		
		Assert.assertNull(rta);		
	}

}
