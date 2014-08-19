package org.calevin.navaja.mapeo;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import junit.framework.Assert;

import org.calevin.navaja.excepciones.mapeo.MapeoClaseNoExisteException;
import org.calevin.navaja.mapeo.RaizMapeo;
import org.calevin.navaja.mapeo.TablaMapeo;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class RaizMapeoTest {

	RaizMapeo raizMapeo;
	String NOMBRETABLA = "TablaMapeoUna";
	String NOMBRETABLACOMOCLASE = "paquete.TablaMapeoUna";
	TablaMapeo tablaMapeoUna = null;
	
	@Before
	public void setUp() throws Exception {
		raizMapeo = new RaizMapeo();
		tablaMapeoUna = new TablaMapeo();
		tablaMapeoUna.setNombre(NOMBRETABLA); 
		tablaMapeoUna.setNombreComoClase(NOMBRETABLACOMOCLASE);
		
		ArrayList<TablaMapeo> tablas = new ArrayList<TablaMapeo>();
		tablas.add(tablaMapeoUna);
		
		raizMapeo.setTablas(tablas);
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

	@Test
	public void getTablaPorNombreClaseCasoRespuestaNula() {
		try {
			TablaMapeo tmp = null;
			tmp = raizMapeo.getTablaPorNombreClase("ninguno");
			fail("No lanzo la exception, tmp = " + tmp.getNombre());
		} catch (Exception e) {
			Assert.assertTrue(e instanceof MapeoClaseNoExisteException);	
		}
	}
}
