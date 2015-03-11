package org.calevin.navaja.sql;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.calevin.navaja.bean.NavajaBean;
import org.calevin.navaja.excepciones.bean.BeanException;
import org.calevin.navaja.excepciones.mapeo.MapeoClaseNoExisteException;
import org.calevin.navaja.excepciones.mapeo.MapeoException;
import org.calevin.navaja.excepciones.sql.CerrarRecursoException;
import org.calevin.navaja.excepciones.sql.PkInvalidaException;
import org.calevin.navaja.mapeo.Mapeador;
import org.calevin.navaja.utiltest.ConstantesParaTests;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class NavajaDAOTest {
	
	private static String INSERT_VALOR_VARCHAR= "insertTest";	
	private static String INSERT_VALOR_VARCHAR_TEST_BORRARME= "insertPk";
	private static String INSERT_VALOR_VARCHAR_TEST_BORRARME_PK_INVALIDA_EXCEPTION= "pkInvalida";
	private static int INSERT_VALOR_INT_TEST_BORRARME= 111;
	private static int INSERT_VALOR_INT_TEST_BORRARME_PK_INVALIDA_EXCEPTION= 222;	
	private static String QUERY_LIMPIAR_MOCK_TABLA_INSERT_VALOR_VARCHAR = "delete from mock_tabla_prueba_dao where atributo_varchar = '" + INSERT_VALOR_VARCHAR +"'";
	private static String QUERY_LIMPIAR_MOCK_TABLA_TEST_DELETE = "delete from mock_tabla_prueba_dao" 
			+ " where atributo_varchar = '" + INSERT_VALOR_VARCHAR_TEST_BORRARME +"' AND atributo_int = " + INSERT_VALOR_INT_TEST_BORRARME;
	
	private static String QUERY_LIMPIAR_MOCK_TABLA_TEST_BORRARME_PK_INVALIDA_EXCEPTION = "delete from mock_tabla_prueba_dao" 
			+ " where atributo_varchar = '" + INSERT_VALOR_VARCHAR_TEST_BORRARME_PK_INVALIDA_EXCEPTION +"' AND atributo_int = " + INSERT_VALOR_INT_TEST_BORRARME_PK_INVALIDA_EXCEPTION;		
	private static String QUERY_LIMPIAR_MOCK_TABLA_TEST_ACTUALIZAME_CASO_CORRECTO = "delete from mock_tabla_prueba_dao where atributo_varchar = 'update' AND atributo_int = 1";	
	private static String QUERY_COMPROBACION_INSERT_VALOR_VARCHAR = "select * from mock_tabla_prueba_dao where atributo_varchar = '" + INSERT_VALOR_VARCHAR +"'";	
	private static String archivoMapeoPruebaDAO = ConstantesParaTests.CARPETA_ARCHIVOS_TEST + "pruebadao/prueba_dao_or.xml";
	private static String archivoMapeoPropertiesDAO = ConstantesParaTests.CARPETA_ARCHIVOS_TEST + "pruebadao/prueba_dao.properties";	

	@BeforeClass 
	static public void setUpClass() throws MapeoException, Exception {
		Mapeador.mapearXml(archivoMapeoPruebaDAO);
		NavajaConector.getInstance().setMapeoRaiz(Mapeador.getRaizMapeo());
		
        NavajaConector.getInstance().setDataSource(proveerDataSource());		
	}

	@AfterClass
	static public void tearDownClass() throws SQLException, CerrarRecursoException {
		NavajaConector.ejecutarUpdate(QUERY_LIMPIAR_MOCK_TABLA_INSERT_VALOR_VARCHAR);
		NavajaConector.ejecutarUpdate(QUERY_LIMPIAR_MOCK_TABLA_TEST_DELETE);
		NavajaConector.ejecutarUpdate(QUERY_LIMPIAR_MOCK_TABLA_TEST_BORRARME_PK_INVALIDA_EXCEPTION);				
		NavajaConector.ejecutarUpdate(QUERY_LIMPIAR_MOCK_TABLA_TEST_ACTUALIZAME_CASO_CORRECTO);
		
		Mapeador.limpiarMapeo();
		NavajaConector.getInstance().setMapeoRaiz(null);
	}

	//TODO agregar casos negativos/comprobar excepciones
	@Test
	public void insertarmeTestCasoCorrecto(){
		try {
			MockClase mockAinsertar = new MockClase(INSERT_VALOR_VARCHAR, 1, 2, 3, 4);

			mockAinsertar.insertarme();

			ResultSet resultComprobacion = NavajaConector.ejecutarQuery(QUERY_COMPROBACION_INSERT_VALOR_VARCHAR);
			if(resultComprobacion.next()){
				MockClase mockParaComprobacion = new MockClase(INSERT_VALOR_VARCHAR, 1, 2, 3, 4);
				mockParaComprobacion.setAtributoString(resultComprobacion.getString("atributo_varchar"));
				mockParaComprobacion.setAtributoInteger(resultComprobacion.getInt("atributo_int"));  
				mockParaComprobacion.setSegundoAtributoInteger(resultComprobacion.getInt("segundo_atributo_int")); 
				mockParaComprobacion.setTercerAtributoInteger(resultComprobacion.getInt("tercer_atributo_int")); 
				mockParaComprobacion.setCuartoAtributoInteger(resultComprobacion.getInt("cuarto_atributo_int")); 
				
				Assert.assertTrue(mockAinsertar.equals(mockParaComprobacion));
				} else {
				fail("No se encontraron registros para comprobar el insert");
			}

		} catch (MapeoClaseNoExisteException e) {
			fail("Excepcion inesperada " + e);
		} catch (SQLException e) {
			fail("Excepcion inesperada " + e);
		} catch (BeanException e) {
			fail("Excepcion inesperada " + e);
		} catch (CerrarRecursoException e) {
			fail("Excepcion inesperada " + e);
		}
	}

	@Test
	public void borrarmeTestCasoCorrecto(){
		try {
		String insert = "INSERT INTO mock_tabla_prueba_dao (atributo_varchar, atributo_int) VALUES ('"
				+ INSERT_VALOR_VARCHAR_TEST_BORRARME + "', " + INSERT_VALOR_INT_TEST_BORRARME +")";
		
		NavajaConector.ejecutarUpdate(insert);
		String consulta = "SELECT * FROM mock_tabla_prueba_dao WHERE " 
				+  "atributo_varchar='"+ INSERT_VALOR_VARCHAR_TEST_BORRARME + "' AND "
				+ "atributo_int="+ INSERT_VALOR_INT_TEST_BORRARME;

		ResultSet rsConsulta = NavajaConector.ejecutarQuery(consulta);
		if (rsConsulta.next()){
			Assert.assertTrue(rsConsulta.getString("atributo_varchar").equals(INSERT_VALOR_VARCHAR_TEST_BORRARME));
			Assert.assertTrue(rsConsulta.getInt("atributo_int") == INSERT_VALOR_INT_TEST_BORRARME);
		} else {
			fail("No se encontro registro en la consulta de borrarmeTest antes del borrado");
		}
		
		MockClase mockAborrar = new MockClase(INSERT_VALOR_VARCHAR_TEST_BORRARME, INSERT_VALOR_INT_TEST_BORRARME, null, null, null);

		mockAborrar.borrarme();
		
		rsConsulta = NavajaConector.ejecutarQuery(consulta);
		if (rsConsulta.next()){
			fail("Se encontro registro en la consulta de borrarmeTest despues del borrado");
		}
		
		} catch (MapeoClaseNoExisteException e) {
			fail("Excepcion inesperada " + e);
		} catch (SQLException e) {
			fail("Excepcion inesperada " + e);
		} catch (BeanException e) {
			fail("Excepcion inesperada " + e);
		} catch (CerrarRecursoException e) {
			fail("Excepcion inesperada " + e);
		} catch (PkInvalidaException e) {
			fail("Excepcion inesperada " + e);
		}
	}

	@Test (expected = PkInvalidaException.class)
	public void borrarmeTestCasoPkInvalidaException(){
		try {
		String insert = "INSERT INTO mock_tabla_prueba_dao (atributo_varchar, atributo_int) VALUES ('"
				+ INSERT_VALOR_VARCHAR_TEST_BORRARME_PK_INVALIDA_EXCEPTION + "', " + INSERT_VALOR_INT_TEST_BORRARME_PK_INVALIDA_EXCEPTION +")";
		
		NavajaConector.ejecutarUpdate(insert);
		String consulta = "SELECT * FROM mock_tabla_prueba_dao WHERE " 
				+  "atributo_varchar='"+ INSERT_VALOR_VARCHAR_TEST_BORRARME_PK_INVALIDA_EXCEPTION + "' AND "
				+ "atributo_int="+ INSERT_VALOR_INT_TEST_BORRARME_PK_INVALIDA_EXCEPTION;

		ResultSet rsConsulta = NavajaConector.ejecutarQuery(consulta);
		if (rsConsulta.next()){
			Assert.assertTrue(rsConsulta.getString("atributo_varchar").equals(INSERT_VALOR_VARCHAR_TEST_BORRARME_PK_INVALIDA_EXCEPTION));
			Assert.assertTrue(rsConsulta.getInt("atributo_int") == INSERT_VALOR_INT_TEST_BORRARME_PK_INVALIDA_EXCEPTION);
		} else {
			fail("No se encontro registro en la consulta de borrarmeTest antes del borrado");
		}
		
		MockClase mockAborrar = new MockClase(null, null, null, null, null);

		mockAborrar.borrarme();
		
		rsConsulta = NavajaConector.ejecutarQuery(consulta);
		if (rsConsulta.next()){
			fail("Se encontro registro en la consulta de borrarmeTest despues del borrado");
		}
		
		} catch (MapeoClaseNoExisteException e) {
			fail("Excepcion inesperada " + e);
		} catch (SQLException e) {
			fail("Excepcion inesperada " + e);
		} catch (BeanException e) {
			fail("Excepcion inesperada " + e);
		} catch (CerrarRecursoException e) {
			fail("Excepcion inesperada " + e);
		} catch (PkInvalidaException e) {
			throw e;
		}
	}
	
	@Test
	public void actualizame(){
		MockClase mockAactualizar;
		try {
			String insert = "INSERT INTO mock_tabla_prueba_dao (atributo_varchar, atributo_int, segundo_atributo_int, tercer_atributo_int, cuarto_atributo_int)"
					+ "VALUES ('update', 1, 12, 13, 14)";

			NavajaConector.ejecutarUpdate(insert);

			String consulta = "SELECT * FROM mock_tabla_prueba_dao WHERE " 
					+  "atributo_varchar='update' AND "
					+ "atributo_int=1";

			ResultSet rsConsulta = NavajaConector.ejecutarQuery(consulta);
			if (rsConsulta.next()){
				Assert.assertTrue(rsConsulta.getString("atributo_varchar").equals("update"));
				Assert.assertTrue(rsConsulta.getInt("atributo_int") == 1);
				Assert.assertTrue(rsConsulta.getInt("segundo_atributo_int") == 12);
				Assert.assertTrue(rsConsulta.getInt("tercer_atributo_int") == 13);
				Assert.assertTrue(rsConsulta.getInt("cuarto_atributo_int") == 14);				
			} else {
				fail("No se encontro registro en la consulta de borrarmeTest antes del borrado");
			}
			
			mockAactualizar = new MockClase("update", 1, 222, 333, 444);

			mockAactualizar.actualizame();
			
			rsConsulta.close();

			rsConsulta = NavajaConector.ejecutarQuery(consulta);
			if (rsConsulta.next()){
				Assert.assertTrue(rsConsulta.getString("atributo_varchar").equals("update"));
				Assert.assertTrue(rsConsulta.getInt("atributo_int") == 1);
				Assert.assertTrue(rsConsulta.getInt("segundo_atributo_int") == 222);
				Assert.assertTrue(rsConsulta.getInt("tercer_atributo_int") == 333);
				Assert.assertTrue(rsConsulta.getInt("cuarto_atributo_int") == 444);				
			} else {
				fail("No se encontro registro en la consulta de borrarmeTest antes del borrado");
			}
			
		} catch (MapeoClaseNoExisteException | BeanException | SQLException | CerrarRecursoException e) {
			fail("Excepcion inesperada " + e);
		}		
	}
	
	@Test
	public void isPkValidaCasoReturnTrue(){
		try {
			MockClase mockConPkValida = new MockClase(INSERT_VALOR_VARCHAR_TEST_BORRARME, INSERT_VALOR_INT_TEST_BORRARME, 1, 2, 3);
			Assert.assertTrue(mockConPkValida.isPkValida());
		} catch (MapeoClaseNoExisteException e) {
			fail("Excepcion inesperada " + e);
		} catch (BeanException e) {
			fail("Excepcion inesperada " + e);
		}
	}
	
	@Test
	public void isPkValidaCasoReturnFalse(){
		try {
			MockClase mockConPkValida = new MockClase(null, INSERT_VALOR_INT_TEST_BORRARME, 1, 2, 3);
			Assert.assertFalse(mockConPkValida.isPkValida());
			mockConPkValida = new MockClase(INSERT_VALOR_VARCHAR_TEST_BORRARME, null, 1, 2, 3);
			Assert.assertFalse(mockConPkValida.isPkValida());

		} catch (MapeoClaseNoExisteException e) {
			fail("Excepcion inesperada " + e);
		} catch (BeanException e) {
			fail("Excepcion inesperada " + e);
		}
	}
	
	//inner class para test
	public class MockClase  extends NavajaDAO implements NavajaBean {
		private String atributoString;
		private Integer atributoInteger;
		private Integer segundoAtributoInteger;
		private Integer tercerAtributoInteger;
		private Integer cuartoAtributoInteger;		
		
		public MockClase() throws MapeoClaseNoExisteException {
			super();
		}

		public MockClase(String atributoString, Integer atributoInteger,
				Integer segundoAtributoInteger, Integer tercerAtributoInteger,
				Integer cuartoAtributoInteger) throws MapeoClaseNoExisteException {
			super();
			this.atributoString = atributoString;
			this.atributoInteger = atributoInteger;
			this.segundoAtributoInteger = segundoAtributoInteger;
			this.tercerAtributoInteger = tercerAtributoInteger;
			this.cuartoAtributoInteger = cuartoAtributoInteger;
		}

		public String getAtributoString() {
			return atributoString;
		}
		
		public void setAtributoString(String atributoString) {
			this.atributoString = atributoString;
		}
		
		public Integer getAtributoInteger() {
			return atributoInteger;
		}
		
		public void setAtributoInteger(Integer atributoInteger) {
			this.atributoInteger = atributoInteger;
		}

		public Integer getSegundoAtributoInteger() {
			return segundoAtributoInteger;
		}

		public void setSegundoAtributoInteger(Integer segundoAtributoInteger) {
			this.segundoAtributoInteger = segundoAtributoInteger;
		}

		public Integer getTercerAtributoInteger() {
			return tercerAtributoInteger;
		}

		public void setTercerAtributoInteger(Integer tercerAtributoInteger) {
			this.tercerAtributoInteger = tercerAtributoInteger;
		}

		public Integer getCuartoAtributoInteger() {
			return cuartoAtributoInteger;
		}

		public void setCuartoAtributoInteger(Integer cuartoAtributoInteger) {
			this.cuartoAtributoInteger = cuartoAtributoInteger;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			//TablaMapeo puede no estar instanciado y por lo tanto no seran iguales
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			MockClase other = (MockClase) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (atributoInteger == null) {
				if (other.atributoInteger != null)
					return false;
			} else if (!atributoInteger.equals(other.atributoInteger))
				return false;
			if (atributoString == null) {
				if (other.atributoString != null)
					return false;
			} else if (!atributoString.equals(other.atributoString))
				return false;
			if (cuartoAtributoInteger == null) {
				if (other.cuartoAtributoInteger != null)
					return false;
			} else if (!cuartoAtributoInteger
					.equals(other.cuartoAtributoInteger))
				return false;
			if (segundoAtributoInteger == null) {
				if (other.segundoAtributoInteger != null)
					return false;
			} else if (!segundoAtributoInteger
					.equals(other.segundoAtributoInteger))
				return false;
			if (tercerAtributoInteger == null) {
				if (other.tercerAtributoInteger != null)
					return false;
			} else if (!tercerAtributoInteger
					.equals(other.tercerAtributoInteger))
				return false;
			return true;
		}

		private NavajaDAOTest getOuterType() {
			return NavajaDAOTest.this;
		}
	}

	//METODO UTILITARIO
	private static DataSource proveerDataSource() throws Exception {
        DataSource dataSource = null;
        Properties propertiesBDD = new Properties();
        
        propertiesBDD.load(new FileInputStream(archivoMapeoPropertiesDAO));

        dataSource = BasicDataSourceFactory.createDataSource(propertiesBDD);

        return dataSource;
	}
}
