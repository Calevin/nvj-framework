package org.calevin.navaja.mapeo;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import junit.framework.Assert;

import org.calevin.navaja.excepciones.mapeo.MapeoClaseNoExisteException;
import org.calevin.navaja.mapeo.RaizMapeo;
import org.calevin.navaja.mapeo.TablaMapeo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class RaizMapeoTest {

	RaizMapeo raizMapeo;
	String NOMBRETABLA = "TablaMapeoUna";
	String NOMBRETABLACOMOCLASE = "paquete.TablaMapeoUna";
	TablaMapeo tablaMapeoUna = null;
	ArrayList<TablaMapeo> tablas = null;
	
	@Before
	public void setUp() throws Exception {
		raizMapeo = new RaizMapeo();
		tablaMapeoUna = new TablaMapeo();
		tablaMapeoUna.setNombre(NOMBRETABLA); 
		tablaMapeoUna.setNombreComoClase(NOMBRETABLACOMOCLASE);
		
		tablas = new ArrayList<TablaMapeo>();
		tablas.add(tablaMapeoUna);
		
		raizMapeo.setTablas(tablas);
	}

	@After 
	public void tearDown() {
		raizMapeo = null;
		tablaMapeoUna = null;
		tablas = null;
	}
	
	@Test
	public void getTablaPorNombreClaseCasoCorrectoTest() {
		try {
			TablaMapeo tmp = null;
			tmp = raizMapeo.getTablaPorNombreClase(NOMBRETABLACOMOCLASE);
			if(tmp != null){
				Assert.assertTrue(tmp.equals(tablaMapeoUna));
				Assert.assertTrue(tmp.getNombre().equals(NOMBRETABLA));
				Assert.assertTrue(tmp.getNombreComoClase().equals(NOMBRETABLACOMOCLASE));
			} else {
				fail("La respuesta fue nula");
			}
		} catch (MapeoClaseNoExisteException e) {
			fail("Exception" + e);
		}
	}

	@Test (expected=MapeoClaseNoExisteException.class)
	public void getTablaPorNombreClaseCasoRespuestaNula() throws MapeoClaseNoExisteException {
		raizMapeo.getTablaPorNombreClase("ninguno");
	}
}
