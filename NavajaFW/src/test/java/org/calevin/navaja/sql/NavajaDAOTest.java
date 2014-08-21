package org.calevin.navaja.sql;

import org.calevin.navaja.bean.NavajaBean;

public class NavajaDAOTest {

	
	
	
	//inner class para test
	public class MockClase  extends NavajaDAO implements NavajaBean {
		private String atributoString;
		private Integer atributoInteger;

		public MockClase() {
		}

		public MockClase(String atributoString, Integer atributoInteger) {
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
