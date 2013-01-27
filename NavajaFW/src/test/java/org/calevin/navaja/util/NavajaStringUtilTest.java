package org.calevin.navaja.util;

import junit.framework.Assert;

import org.calevin.navaja.util.NavajaStringUtil;
import org.junit.Before;
import org.junit.Test;

public class NavajaStringUtilTest {

	private String STRING_LETRAS_MINUSCULAS = "string";
	private String STRING_PRIMERA_LETRA_MAYUSCULA = "String";	
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void conmutarCaseCharCasoCorrectoTest() {
		String rta = NavajaStringUtil.conmutarCaseChar(STRING_LETRAS_MINUSCULAS, 0);
		
		Assert.assertTrue((rta.equals(STRING_PRIMERA_LETRA_MAYUSCULA)));

		rta = NavajaStringUtil.conmutarCaseChar(STRING_PRIMERA_LETRA_MAYUSCULA, 0);
		
		Assert.assertTrue((rta.equals(STRING_LETRAS_MINUSCULAS)));		
	
	}
	
	@Test
	public void conmutarCaseCharRespuestaNulaTest() {
		String rta = NavajaStringUtil.conmutarCaseChar(STRING_LETRAS_MINUSCULAS, -1);
		
		Assert.assertTrue((rta == null));
		
		rta = NavajaStringUtil.conmutarCaseChar(STRING_LETRAS_MINUSCULAS, 500);
		
		Assert.assertTrue((rta == null));		
	}

}
