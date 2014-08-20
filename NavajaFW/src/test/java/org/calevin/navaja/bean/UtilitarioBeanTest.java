package org.calevin.navaja.bean;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;

import junit.framework.Assert;

import org.calevin.navaja.excepciones.bean.BeanException;
import org.calevin.navaja.excepciones.bean.BeanInvocarGetterException;
import org.calevin.navaja.excepciones.bean.BeanInvocarSetterException;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class UtilitarioBeanTest {
	
	private MockClase mockClase = null;
	private String atributoStringNombre = "atributoString";
	private String atributoStringValor = "atributoValorString";
	private String atributoInexistente = "inexistente";
	private String atributoStringValorAlternativo = "atributoValorAlternativoString";	
	private String atributoIntegerNombre = "atributoInteger";
	private Integer atributoIntegerValor = 123;	
	private Integer atributoIntegerValorAlternativo = 321;	
	private ArrayList<String> atributosNombres = new ArrayList<String>();
	@Before
	public void setUp() throws Exception {
		mockClase = new MockClase();
		atributosNombres.add(atributoIntegerNombre);
		atributosNombres.add(atributoStringNombre);
	}
	
	// TESTS PARA invocarGetter:
	@Test
	public void invocarGetterCasoCorrectoTest(){
		try {
			mockClase = new MockClase(atributoStringValor, atributoIntegerValor);
			Assert.assertTrue(atributoStringValor.equals((String) UtilitarioBean.invocarGetter(mockClase, atributoStringNombre)));
			Assert.assertTrue(atributoIntegerValor.equals((Integer) UtilitarioBean.invocarGetter(mockClase, atributoIntegerNombre)));			
		} catch (BeanException e) {
			fail("Exception! " + e);
		}
		
	}

	@Test (expected=BeanInvocarGetterException.class)
	public void invocarGetterCasoInstanciaNulaTest() throws Exception {
		UtilitarioBean.invocarGetter(null, atributoStringNombre);			
	}	

	@Test (expected=BeanInvocarGetterException.class)
	public void invocarGetterCasoAtributoNuloTest() throws Exception {
		mockClase = new MockClase(atributoStringValor, atributoIntegerValor);		
		UtilitarioBean.invocarGetter(mockClase, null);
	}

	@Test
	public void invocarGetterCasoAtributoInexistenteTest() {
		mockClase = new MockClase(atributoStringValor, atributoIntegerValor);
		try {
			Assert.assertNull(UtilitarioBean.invocarGetter(mockClase, atributoInexistente));
		} catch (BeanException e) {
			fail("Exception! " + e);
		}
	}
	
	//TESTS PARA invocarSetter
	@Test
	public void invocarSetterCasoCorrectoTest(){
		mockClase = new MockClase(atributoStringValor, atributoIntegerValor);		
		try {
			//Seteo el valor alternativo para el atributo tipo String
			UtilitarioBean.invocarSetter(mockClase, atributoStringNombre, atributoStringValorAlternativo);
			//Compruebo el valor alternativo para el atributo tipo String
			Assert.assertTrue(atributoStringValorAlternativo.equals((String) UtilitarioBean.invocarGetter(mockClase, atributoStringNombre)));

			//Seteo el valor alternativo para el atributo tipo Integer
			UtilitarioBean.invocarSetter(mockClase, atributoIntegerNombre, atributoIntegerValorAlternativo);
			//Compruebo el valor alternativo para el atributo tipo Integer
			Assert.assertTrue(atributoIntegerValorAlternativo.equals((Integer) UtilitarioBean.invocarGetter(mockClase, atributoIntegerNombre)));
			
		} catch (BeanException e) {
			fail("Exception! " + e);
		}
		
	}

	@Test
	public void invocarSetterCasoCorrectoValorNuloTest(){
		mockClase = new MockClase(atributoStringValor, atributoIntegerValor);		
		try {
			//Seteo el valor alternativo para el atributo tipo String
			UtilitarioBean.invocarSetter(mockClase, atributoStringNombre, null);
			//Compruebo el valor alternativo para el atributo tipo String
			Assert.assertTrue(((String) UtilitarioBean.invocarGetter(mockClase, atributoStringNombre)) == null);

			//Seteo el valor alternativo para el atributo tipo Integer
			UtilitarioBean.invocarSetter(mockClase, atributoIntegerNombre, null);
			//Compruebo el valor alternativo para el atributo tipo Integer
			Assert.assertTrue(((Integer) UtilitarioBean.invocarGetter(mockClase, atributoIntegerNombre)) == null);
			
		} catch (BeanException e) {
			fail("Exception! " + e);
		}
		
	}
	
	@Test (expected=BeanInvocarSetterException.class)
	public void invocarSetterCasoInstanciaNulaTest() throws Exception {		
		UtilitarioBean.invocarSetter(null, atributoStringNombre, atributoStringValorAlternativo);			
	}		

	@Test (expected=BeanInvocarSetterException.class) 
	public void invocarSetterCasoAtributoNuloTest() throws Exception{
		mockClase = new MockClase(atributoStringValor, atributoIntegerValor);		
		UtilitarioBean.invocarSetter(mockClase, null, atributoStringValorAlternativo);
	}

	@Test
	public void invocarSetterCasoAtributoInexistenteTest() {
		mockClase = new MockClase(atributoStringValor, atributoIntegerValor);
		try {
			UtilitarioBean.invocarSetter(mockClase, atributoInexistente, atributoStringValorAlternativo);
		} catch (BeanException e) {
			fail("Exception! " + e);
		}
		
		Assert.assertTrue(atributoStringValor.equals(mockClase.getAtributoString()));
		Assert.assertTrue(atributoIntegerValor.equals(mockClase.getAtributoInteger()));	
	}
	
	@Test
	public void listarAttsCasoCorrectoTest(){
		ArrayList<String> rtalistarAtts = new ArrayList<String>();
		rtalistarAtts = UtilitarioBean.listarAtts(MockClase.class);
		
		Collections.sort(rtalistarAtts);
		Collections.sort(atributosNombres);

		Assert.assertTrue(rtalistarAtts.containsAll(atributosNombres));
	}

	@Test
	public void listarAttsCasoClaseSinAtributosAccesiblesTest(){
		ArrayList<String> rtalistarAtts = new ArrayList<String>();
		rtalistarAtts = UtilitarioBean.listarAtts(MockClaseSinAtributosAccesibles.class);

		Assert.assertTrue(rtalistarAtts.isEmpty());
	}
	
	public class MockClase {
		private String atributoString;
		private Integer atributoInteger;

		public MockClase() {
		}

		public MockClase(String atributoString, Integer atributoInteger) {
			super();
			this.atributoString = atributoString;
			this.atributoInteger = atributoInteger;
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
	}
	
	@SuppressWarnings("unused")
	public class MockClaseSinAtributosAccesibles {
		
		private String atributoStringNoAccesible;
		private Integer atributoIntegerNoAccesible;
		
		public void setAtributoStringNoAccesible(String atributoStringNoAccesible) {
			this.atributoStringNoAccesible = atributoStringNoAccesible;
		}

	}
}
