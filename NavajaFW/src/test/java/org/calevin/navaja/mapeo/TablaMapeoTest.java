package org.calevin.navaja.mapeo;

import java.util.ArrayList;

import junit.framework.Assert;

import org.calevin.navaja.mapeo.CampoMapeo;
import org.calevin.navaja.mapeo.TablaMapeo;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class TablaMapeoTest {

	final String UNO = "uno";
	final String DOS = "dos";
	final String UNOCOMOATT = "unoComoAtt";
	final String DOSCOMOATT = "dosComoAtt";
	
	TablaMapeo tablaMapeo = new TablaMapeo();
	CampoMapeo campoPrimero = new CampoMapeo(UNO, UNOCOMOATT);
	CampoMapeo campoSegundo = new CampoMapeo(DOS, DOSCOMOATT);

	@Before
	public void setUp() {

		ArrayList<CampoMapeo> campos = new ArrayList<CampoMapeo>();
		campos.add(campoPrimero);
		campos.add(campoSegundo);

		tablaMapeo.setCampos(campos);
	}

	@Test
	public void getCampoMapeoPorNombreCasoCorrectoTest() {
		Assert.assertTrue(campoPrimero.equals(tablaMapeo.getCampoMapeoPorNombre(UNO)));
	}

	@Test
	public void getCampoMapeoPorNombreCasoRespuestaNulaTest() {
		Assert.assertNull(tablaMapeo.getCampoMapeoPorNombre("ninguno"));
	}

	@Test
	public void getCampoMapeoPorAtributoCasoCorrectoTest(){
		Assert.assertTrue(campoPrimero.equals(tablaMapeo.getCampoMapeoPorAtributo(UNOCOMOATT)));
	}

	@Test
	public void getCampoMapeoPorAtributoCasoRespuestaNulaTest(){
		Assert.assertNull(tablaMapeo.getCampoMapeoPorAtributo("ninguno"));
	}
}
