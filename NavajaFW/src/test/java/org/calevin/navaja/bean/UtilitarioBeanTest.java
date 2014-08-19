package org.calevin.navaja.bean;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;

import junit.framework.Assert;

import org.calevin.navaja.excepciones.bean.BeanException;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class UtilitarioBeanTest {
	
	private MockClase mockClase = null;
	private String atributoStringNombre = "atributoString";
	private String atributoStringValor = "atributoValorString";
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

	@Test
	public void invocarSetterCasoCorrectoTest(){
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
	public void listarAttsCasoCorrectoTest(){
		ArrayList<String> rtalistarAtts = new ArrayList<String>();
		rtalistarAtts = UtilitarioBean.listarAtts(MockClase.class);
		
		Collections.sort(rtalistarAtts);
		Collections.sort(atributosNombres);

		Assert.assertTrue(rtalistarAtts.containsAll(atributosNombres));
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
}
