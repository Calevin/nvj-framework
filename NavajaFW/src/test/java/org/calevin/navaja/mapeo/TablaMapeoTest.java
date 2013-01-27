package org.calevin.navaja.mapeo;

import java.util.ArrayList;

import junit.framework.Assert;

import org.calevin.navaja.mapeo.CampoMapeo;
import org.calevin.navaja.mapeo.TablaMapeo;
import org.junit.Before;
import org.junit.Test;

public class TablaMapeoTest {

	final String UNO = "uno";
	final String DOS = "dos";
	final String UNOCOMOATT = "unoComoAtt";
	final String DOSCOMOATT = "dosComoAtt";
	
	TablaMapeo tablaMapeo = new TablaMapeo();
	CampoMapeo campoPrimero = new CampoMapeo(UNO, UNOCOMOATT);
	CampoMapeo campoSegundo = new CampoMapeo(DOS, DOSCOMOATT);

	@Before
	public void setUp() throws Exception {

		ArrayList<CampoMapeo> campos = new ArrayList<CampoMapeo>();
		campos.add(campoPrimero);
		campos.add(campoSegundo);

		tablaMapeo.setCampos(campos);
	}

	@Test
	public void getCampoMapeoPorNombreCasoCorrectoTest() {

		CampoMapeo campoMapeoTest;
		campoMapeoTest = tablaMapeo.getCampoMapeoPorNombre(UNO);

		Assert.assertTrue("El CampoMapeo retornado fue distinto", (campoMapeoTest.equals(campoPrimero)));

	}

	@Test
	public void getCampoMapeoPorNombreCasoRespuestaNulaTest() {

		CampoMapeo campoMapeoTest;
		campoMapeoTest = tablaMapeo.getCampoMapeoPorNombre("ninguno");

		Assert.assertTrue("El CampoMapeo retornado fue distinto", (campoMapeoTest == null));

	}

	@Test
	public void getCampoMapeoPorAtributoCasoCorrectoTest(){

		CampoMapeo campoMapeoTest;
		campoMapeoTest = tablaMapeo.getCampoMapeoPorAtributo(UNOCOMOATT);
		
		Assert.assertTrue("El CampoMapeo retornado fue distinto", (campoMapeoTest.equals(campoPrimero)));
		
		
	}

	@Test
	public void getCampoMapeoPorAtributoCasoRespuestaNulaTest(){

		CampoMapeo campoMapeoTest;
		campoMapeoTest = tablaMapeo.getCampoMapeoPorAtributo("ninguno");
		
		Assert.assertTrue("El CampoMapeo retornado fue distinto", (campoMapeoTest == null));
		
		
	}
}
