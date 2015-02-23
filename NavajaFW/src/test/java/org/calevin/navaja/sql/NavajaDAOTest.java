package org.calevin.navaja.sql;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.calevin.navaja.bean.NavajaBean;
import org.calevin.navaja.excepciones.bean.BeanException;
import org.calevin.navaja.excepciones.mapeo.MapeoClaseNoExisteException;
import org.calevin.navaja.excepciones.mapeo.MapeoException;
import org.calevin.navaja.excepciones.sql.CerrarRecursoException;
import org.calevin.navaja.mapeo.Mapeador;
import org.calevin.navaja.utiltest.ConstantesParaTests;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class NavajaDAOTest {
	
	private static String INSERT_VALOR_VARCHAR= "insertTest";	
	private static String QUERY_LIMPIAR_MOCK_TABLA_INSERT_VALOR_VARCHAR = "delete from mock_tabla_prueba_dao where atributo_varchar = '" + INSERT_VALOR_VARCHAR +"'";
	private static String archivoMapeoPruebaDAO = ConstantesParaTests.CARPETA_ARCHIVOS_TEST + "pruebadao/prueba_dao_or.xml";
	private static String archivoMapeoPropertiesDAO = ConstantesParaTests.CARPETA_ARCHIVOS_TEST + "pruebadao/prueba_dao.properties";	

	@BeforeClass 
	static public void setUpClass() throws MapeoException {
		Mapeador.mapearXml(archivoMapeoPruebaDAO);
		NavajaConector.getInstance().setMapeoRaiz(Mapeador.getRaizMapeo());
		
        NavajaConector.getInstance().setDataSource(proveerDataSource());		
	}

	@AfterClass
	static public void tearDownClass() throws SQLException, CerrarRecursoException {
		Connection con = proveerDataSource().getConnection();
	    PreparedStatement pstm = con.prepareStatement(QUERY_LIMPIAR_MOCK_TABLA_INSERT_VALOR_VARCHAR);	
	    pstm.executeUpdate();			

		Mapeador.limpiarMapeo();
		NavajaConector.getInstance().setMapeoRaiz(null);
		NavajaConector.cerrarRecurso(con);
		NavajaConector.cerrarRecurso(pstm);
	}
	
	//TODO agregar el tearDown y el setUp
	
	//TODO agregar la comprobacion
	@Test
	public void insertarmeTest(){
		MockClase mockAinsertar = new MockClase(INSERT_VALOR_VARCHAR, 1, 2, 3, 4);
		
		try {
			mockAinsertar.insertarme();
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
	
	//inner class para test
	public class MockClase  extends NavajaDAO implements NavajaBean {
		private String atributoString;
		private Integer atributoInteger;
		private Integer segundoAtributoInteger;
		private Integer tercerAtributoInteger;
		private Integer cuartoAtributoInteger;		
		
		public MockClase() {
		}

		public MockClase(String atributoString, Integer atributoInteger,
				Integer segundoAtributoInteger, Integer tercerAtributoInteger,
				Integer cuartoAtributoInteger) {
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
	}

	//METODO UTILITARIO
	private static DataSource proveerDataSource() {
        DataSource dataSource = null;
        Properties propertiesBDD = new Properties();
        try {
            propertiesBDD.load(new FileInputStream(archivoMapeoPropertiesDAO));

            dataSource = BasicDataSourceFactory.createDataSource(propertiesBDD);
        } catch (IOException ex) {
            System.out.println("Error al cargar archivo: " + ex);
        } catch (Exception ex) {
            System.out.println("Error inesperado: " + ex);
        }
            return dataSource;
	}
}
