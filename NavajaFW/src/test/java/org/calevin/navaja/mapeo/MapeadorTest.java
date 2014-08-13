package org.calevin.navaja.mapeo;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class MapeadorTest {

	private String archivoPruebaOr = "src/test/java/org/calevin/navaja/archivos/prueba_or.xml";
    private ArrayList<TablaMapeo> tablas = null;
    private ArrayList<String> nombresClase = null;	
    
    private String nombreClase = "org.empresa.beans.Prueba";
    private TablaMapeo tabla = null;
    private PrimaryKeyMapeo pk = null;
    
	@Before
	public void setUp() throws Exception {
		Mapeador.mapearXml(archivoPruebaOr);
		
		tablas = new ArrayList<TablaMapeo>();
		nombresClase = new ArrayList<String>();
		tabla = new TablaMapeo();
		tabla.setNombre("prueba");
		tabla.setNombreComoClase("org.empresa.beans.Prueba");
		tabla.getCampos().add(0, new CampoMapeo("id","id"));
		tabla.getCampos().add(1, new CampoMapeo("des","des"));
		pk = new PrimaryKeyMapeo();
		pk.getCampos().add(new CampoMapeo("id",null));
		tabla.setPrimaryKeyMapeo(pk);
	}
	
	@Test
	public void test() {
		nombresClase = Mapeador.getMapeoRaiz().getNombresClase();
		Assert.assertTrue(nombresClase.size() == 1);
		Assert.assertTrue(nombresClase.get(0).equals(nombreClase));	
		
		tablas = Mapeador.getMapeoRaiz().getTablas();
		Assert.assertTrue(tablas.size() == 1);
		TablaMapeo tablaMapeada = tablas.get(0);
		Assert.assertTrue(tablaMapeada.equals(tabla));
		tabla.setCampos(null);
		Assert.assertFalse(tablaMapeada.equals(tabla));		
	}

}
