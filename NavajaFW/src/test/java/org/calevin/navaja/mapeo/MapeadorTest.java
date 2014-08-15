package org.calevin.navaja.mapeo;

import java.util.ArrayList;

import junit.framework.Assert;

import org.calevin.navaja.excepciones.mapeo.MapeoCampoRepetidoException;
import org.calevin.navaja.excepciones.mapeo.MapeoClaseRepetidaException;
import org.calevin.navaja.excepciones.mapeo.MapeoException;
import org.calevin.navaja.excepciones.mapeo.MapeoTablaRepetidaException;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class MapeadorTest {

	private String archivoPruebaUnaTabla = "src/test/java/org/calevin/navaja/archivos/prueba_una_tabla_or.xml";
	private String archivoPruebaVariasTablas = "src/test/java/org/calevin/navaja/archivos/prueba_varias_tablas_or.xml";
	private String archivoPruebaPkVariasCols = "src/test/java/org/calevin/navaja/archivos/prueba_pk_varias_cols_or.xml";	
	private String archivoPruebaTablaRepetida = "src/test/java/org/calevin/navaja/archivos/prueba_tabla_repetida_or.xml";
	private String archivoPruebaColumnaRepetida = "src/test/java/org/calevin/navaja/archivos/prueba_columna_repetida_or.xml";
	private String archivoPruebaClaseRepetida = "src/test/java/org/calevin/navaja/archivos/prueba_clase_repetida_or.xml";
	private String archivoPruebaMapeoVacio = "src/test/java/org/calevin/navaja/archivos/prueba_mapeo_vacio_or.xml";
	
    private ArrayList<TablaMapeo> tablas = null;
    private ArrayList<String> nombresClase = null;	
    
    private String nombreClasePrimera = "org.empresa.beans.Prueba";
    private String nombreClaseSegunda = "org.empresa.beans.PruebaSegundaTabla";    
    private TablaMapeo tablaPrimera = null;
    private TablaMapeo tablaSegunda = null;
    private PrimaryKeyMapeo pk = null;
    private PrimaryKeyMapeo pkSegunda = null;
    private PrimaryKeyMapeo pkVariasColumnas = null;    
       
	@Before 
	public void setUp() throws Exception {

		tablas = new ArrayList<TablaMapeo>();
		nombresClase = new ArrayList<String>();
		//Tablas
		tablaPrimera = new TablaMapeo();
		tablaPrimera.setNombre("prueba");
		tablaPrimera.setNombreComoClase(nombreClasePrimera);
		tablaPrimera.getCampos().add(0, new CampoMapeo("id","id"));
		tablaPrimera.getCampos().add(1, new CampoMapeo("des","des"));
		pk = new PrimaryKeyMapeo();
		pk.getCampos().add(new CampoMapeo("id",null));
		tablaPrimera.setPrimaryKeyMapeo(pk);
		
		tablaSegunda = new TablaMapeo();
		tablaSegunda.setNombre("prueba_segunda_tabla");
		tablaSegunda.setNombreComoClase(nombreClaseSegunda);
		tablaSegunda.getCampos().add(0, new CampoMapeo("id_segunda","id_segunda"));
		tablaSegunda.getCampos().add(1, new CampoMapeo("des_segunda","des_segunda"));
		pkSegunda = new PrimaryKeyMapeo();
		pkSegunda.getCampos().add(new CampoMapeo("id_segunda",null));
		tablaSegunda.setPrimaryKeyMapeo(pkSegunda);		
	}
	
	@Test
	public void mapeoCorrectoUnaTabla() throws Exception {
		Mapeador.limpiarMapeo();

		Mapeador.mapearXml(archivoPruebaUnaTabla);
		
		//Probando nombreClases
		nombresClase = Mapeador.getMapeoRaiz().getNombresClase();
		Assert.assertTrue(nombresClase.size() == 1);
		Assert.assertTrue(nombresClase.get(0).equals(nombreClasePrimera));	

		//Probando Tablas
		tablas = Mapeador.getMapeoRaiz().getTablas();
		Assert.assertTrue(tablas.size() == 1);
		TablaMapeo tablaMapeada = tablas.get(0);
		Assert.assertTrue(tablaMapeada.equals(tablaPrimera));
		Assert.assertTrue(tablaMapeada.getPrimaryKeyMapeo().equals(pk));

	}

	@Test
	public void mapeoCorrectoVariasTabla() throws Exception{
		Mapeador.limpiarMapeo();

		Mapeador.mapearXml(archivoPruebaVariasTablas);

		//Probando nombreClases
		nombresClase = Mapeador.getMapeoRaiz().getNombresClase();
		Assert.assertTrue(nombresClase.size() == 2);
		Assert.assertTrue(nombresClase.get(0).equals(nombreClasePrimera));
		Assert.assertTrue(nombresClase.get(1).equals(nombreClaseSegunda));
		
		//Probando Tablas
		tablas = Mapeador.getMapeoRaiz().getTablas();
		Assert.assertTrue(tablas.size() == 2);
		//Primera Tabla
		TablaMapeo tablaMapeada = tablas.get(1);
		Assert.assertTrue(tablaMapeada.equals(tablaPrimera));
		Assert.assertTrue(tablaMapeada.getPrimaryKeyMapeo().equals(pk));
		//Segunda Tabla		
		tablaMapeada = tablas.get(0);
		Assert.assertTrue(tablaMapeada.equals(tablaSegunda));
		Assert.assertTrue(tablaMapeada.getPrimaryKeyMapeo().equals(pkSegunda));
	
	}
	
	@Test
	public void mapeoCorrectoPkVariasColumnas() throws Exception {
		Mapeador.limpiarMapeo();

		Mapeador.mapearXml(archivoPruebaPkVariasCols);

		//Probando nombreClases
		nombresClase = Mapeador.getMapeoRaiz().getNombresClase();
		Assert.assertTrue(nombresClase.size() == 1);
		Assert.assertTrue(nombresClase.get(0).equals(nombreClasePrimera));	

		//Probando Tablas
		tablas = Mapeador.getMapeoRaiz().getTablas();
		Assert.assertTrue(tablas.size() == 1);
		TablaMapeo tablaMapeada = tablas.get(0);

		//Probando Pk Varias Columnas
		pkVariasColumnas = new PrimaryKeyMapeo();
		pkVariasColumnas.getCampos().add(new CampoMapeo("id",null));
		pkVariasColumnas.getCampos().add(new CampoMapeo("des",null));
		Assert.assertTrue(tablaMapeada.getPrimaryKeyMapeo().equals(pkVariasColumnas));

	}
	
	@Test (expected=MapeoTablaRepetidaException.class)
	public void mapeoErroneoTablaRepetida() throws Exception {
		Mapeador.limpiarMapeo();
		Mapeador.mapearXml(archivoPruebaTablaRepetida);
	
	}
	
	@Test(expected = MapeoCampoRepetidoException.class)
	public void mapeoErroneoColumnaRepetida() throws Exception {
		Mapeador.limpiarMapeo();
		Mapeador.mapearXml(archivoPruebaColumnaRepetida);	
	}
	
	@Test(expected = MapeoClaseRepetidaException.class)
	public void mapeoErroneoClaseRepetida() throws Exception {
		Mapeador.limpiarMapeo();
		Mapeador.mapearXml(archivoPruebaClaseRepetida);	
	}
	
	@Test(expected = MapeoException.class)
	public void mapeoArchivoVacio() throws Exception {
		Mapeador.limpiarMapeo();
		Mapeador.mapearXml(archivoPruebaMapeoVacio);
	}
}
