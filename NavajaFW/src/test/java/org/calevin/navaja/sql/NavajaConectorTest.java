package org.calevin.navaja.sql;

import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.calevin.navaja.excepciones.mapeo.MapeoException;
import org.calevin.navaja.excepciones.sql.CerrarRecursoException;
import org.calevin.navaja.utiltest.ConstantesParaTests;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class NavajaConectorTest {
	private static String archivoMapeoProperties = ConstantesParaTests.CARPETA_ARCHIVOS_TEST + "pruebaconector/prueba_conector.properties";

	private static String QUERY_SELECT_DATABASE = "select database();";
	private static String QUERY_CREATE_TABLE = "CREATE TABLE test_create_tabla (columna int)";
	private static String QUERY_INSERT_TEST_CREATE_TABLA = "INSERT INTO test_create_tabla VALUES (1)";
	private static String QUERY_DROP_TABLE_TEST_CREATE = "DROP TABLE test_create_tabla;";
	private static String DATABASE_NOMBRE = "test_database";
	private static String COLUMNA_CONSULTA_DATABASE = "database()";
	
	@BeforeClass 
	static public void setUpClass() throws MapeoException {
        NavajaConector.getInstance().setDataSource(proveerDataSource());
	}
	
	@AfterClass
	static public void tearDownClass() throws SQLException {
        NavajaConector.getInstance().setDataSource(null);
        
        dropTablaTestCreate();
	}
	
	//TESTs
	@Test
	public void ejecutarQueryReturnResultSetCasoCorrectoTest(){
		ResultSet rs = NavajaConector.ejecutarQuery(QUERY_SELECT_DATABASE);
		
		try {
			rs.next();
			
			Assert.assertTrue(DATABASE_NOMBRE.equals((String)rs.getObject(COLUMNA_CONSULTA_DATABASE)));

			NavajaConector.cerrarRecurso(rs);
		} catch (SQLException e) {
			fail("Exception! " + e);
		} catch (CerrarRecursoException e) {
			fail("Exception! " + e);
		}
	}
	
	@Test
	public void ejecutarQueryReturnIntCasoCorrectoTest(){
		int rowsModificados = NavajaConector.ejecutarUpdate(QUERY_CREATE_TABLE);
		Assert.assertTrue(0 == rowsModificados);
		rowsModificados = NavajaConector.ejecutarUpdate(QUERY_INSERT_TEST_CREATE_TABLA);
		Assert.assertTrue(1 == rowsModificados);
	}
	
	//METODOS UTILITARIOS:
	private static void dropTablaTestCreate(){
        Connection con = null;
        PreparedStatement pstm = null;
        try {
        con = proveerDataSource().getConnection();
        pstm = con.prepareStatement(QUERY_DROP_TABLE_TEST_CREATE);
		} catch (SQLException e) {
			System.out.println("Error creando la query " + e);
		}

        try {
        System.out.println("Por ejecutar " + pstm.toString());
        pstm.executeUpdate();
        System.out.println("Ejecutado ");
		} catch (SQLException e) {
			System.out.println("Error al ejecutar la query " + e);
		}
        
        try {
        	NavajaConector.cerrarRecurso(con);
        	NavajaConector.cerrarRecurso(pstm);
		} catch (CerrarRecursoException e) {
			System.out.println("Error cerrando recursos " + e);
		}
	}
	
	private static DataSource proveerDataSource() {
        DataSource dataSource = null;
        Properties propertiesBDD = new Properties();
        try {
            propertiesBDD.load(new FileInputStream(archivoMapeoProperties));

            dataSource = BasicDataSourceFactory.createDataSource(propertiesBDD);
        } catch (IOException ex) {
            System.out.println("Error al cargar archivo: " + ex);
        } catch (Exception ex) {
            System.out.println("Error inesperado: " + ex);
        }
            return dataSource;
	}

}
