package org.calevin.navaja.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.calevin.navaja.bean.UtilitarioBean;
import org.calevin.navaja.excepciones.bean.BeanException;
import org.calevin.navaja.excepciones.mapeo.MapeoClaseNoExisteException;
import org.calevin.navaja.excepciones.sql.CerrarRecursoException;
import org.calevin.navaja.mapeo.CampoMapeo;
import org.calevin.navaja.mapeo.TablaMapeo;

public class NavajaDAO {

    protected TablaMapeo tablaMapeo = null;
   
    public NavajaDAO() throws MapeoClaseNoExisteException {
    	super();
    	//Se carga la tabla al crear la instancia
    	//sera utilizada posteriorme, y es necesaria para el metodo equals
    	//MapeoClaseNoExisteException solo puede ser lanzada en este momento
    	cargarTablaMapeo();
    }
    
    /**
     * Define el atributo tablaMapeo de la clase a partir del nombre de la misma, obteniendolo del mapeo general desde NavajaConector.
     * @throws MapeoClaseNoExisteException
     */
    private void cargarTablaMapeo() throws MapeoClaseNoExisteException{
    	if (this.tablaMapeo == null){
    		this.tablaMapeo = NavajaConector.getInstance().getRaizMapeo().getTablaPorNombreClase(this.getClass().getName());    		
    	}
    }
    
    /**
     * Inserta en la base de datos un registro identico a dicha instancia.
     * @throws SQLException 
     * @throws BeanException 
     * @throws CerrarRecursoException 
     */
    public void insertarme() throws SQLException, BeanException, CerrarRecursoException {
        
        Connection con = null;
        PreparedStatement pstm = null;
         
        //Se obtiene la cantidad de campos
        int cantidadCampos = tablaMapeo.getCampos().size();
        //Se obtiene el nombre de la tabla
        String tablaNombre = tablaMapeo.getNombre();        
        
        //Se crea el insert a partir de nombre de la tabla y de listar todos sus  campos
        String insercion = "";

        String insercionInsertInto = "INSERT INTO " + tablaNombre;
        String insercionColumnas = " (";
        String insercionValues = "VALUES (";
        //Se obtiene los campos de la tabla
        ArrayList<String> campos = listaCamposParaQuery();
        //El insert tiene la forma:
        //INSERT INTO nombre_tabla
        //(columna1, columna2,columna3)
        //VALUES (?, ?, ?)
        for (int i = 0; i < cantidadCampos; i++) {
        	//Despues del primer item se ingresa una coma antes de cada nuevo item
            if (i != 0){
            	insercionColumnas += ", ";
            	insercionValues += ", ";
            }
            insercionColumnas += campos.get(i);
       		insercionValues += "?";
        }
        insercionColumnas += ") ";
        insercionValues += ") ";
        //Se completa el INSERT con todas las partes de la query
        insercion = insercionInsertInto + insercionColumnas + insercionValues;
        
		con = NavajaConector.getInstance().getDataSource().getConnection();
	    pstm = con.prepareStatement(insercion);
	    //Se setean los parametros obtienendo los atributos de la instancia
	    for (int i = 0; i < cantidadCampos; i++) {
	    	//a partir del campo se obtiene el valor de dicho atributo
	    	String campo = campos.get(i);
	    	String atributo = tablaMapeo.getCampoMapeoPorNombre(campo).getNombreComoAtributo();
	    	//Los parametros del PrepareStatement se inician en 1 por eso se usa i+1
	    	pstm.setObject(i+1, UtilitarioBean.invocarGetter(this, atributo));	
	    }
	    
        System.out.println("\nInsercion: " + insercion + ") ");

        System.out.println("Se ejecutara: " + pstm.toString());

		System.out.println("Cambios por la ejecucion: " + pstm.executeUpdate());

		NavajaConector.cerrarRecurso(con);
		NavajaConector.cerrarRecurso(pstm);
    }

    /**
     * Borra de la base de datos el registro correspondiente con la pk de la instancia.
     * @throws SQLException 
     * @throws BeanException 
     * @throws CerrarRecursoException 
     */
    public void borrarme() throws SQLException, BeanException, CerrarRecursoException {
        
        Connection con = null;
        PreparedStatement pstm = null;
        
    	String delete = "";
        //Se obtiene el nombre de la tabla
        String tablaNombre = tablaMapeo.getNombre();
        
        //Se obtiene la cantidad de campos de la pk
        int cantidadCamposPk = tablaMapeo.getPrimaryKeyMapeo().getCampos().size();
        
    	String deleteFrom = "DELETE FROM " + tablaNombre + " ";
    	String deleteWherePk = "WHERE ";
        //Se obtiene los campos de la pk de la tabla
        ArrayList<String> camposPk = listaCamposPkParaQuery();     	
    	//El delete tiene la forma:
    	//DELETE FROM nombre_tabla
    	//WHERE columnapk1 = ?,
    	//columnapk1 = ?
    	//...
        for (int i = 0; i < cantidadCamposPk; i++) {
        	//Despues del primer item se ingresa una coma antes de cada nuevo item
            if (i != 0){
            	deleteWherePk += " AND ";
            }
            deleteWherePk  += camposPk.get(i) + " = ?";
        }
        delete = deleteFrom + deleteWherePk;

		con = NavajaConector.getInstance().getDataSource().getConnection();
	    pstm = con.prepareStatement(delete);
	    //Se setean los parametros obtienendo los atributos de la instancia
	    for (int i = 0; i < cantidadCamposPk; i++) {
	    	//a partir del campo se obtiene el valor de dicho atributo
	    	String campoPk = camposPk.get(i);
	    	String atributo = tablaMapeo.getCampoMapeoPorNombre(campoPk).getNombreComoAtributo();
	    	//Los parametros del PrepareStatement se inician en 1 por eso se usa i+1
	    	pstm.setObject(i+1, UtilitarioBean.invocarGetter(this, atributo));	
	    }        
	    
	    
        System.out.println("\nDelete: " + delete + " ");

        System.out.println("Se ejecutara: " + pstm.toString());

		System.out.println("Cambios por la ejecucion: " + pstm.executeUpdate());

		NavajaConector.cerrarRecurso(con);
		NavajaConector.cerrarRecurso(pstm);	    
    }
    
    /**
     * Lista todos los campos de una tabla
     * @return campos un ArrayList<String> con los campos de la tabla
     */
    public ArrayList<String> listaCamposParaQuery() {
    	ArrayList<String> campos = new ArrayList<String>();

        int cantidadCampos = tablaMapeo.getCampos().size();
        
        for (int i = 0; i < cantidadCampos; i++) {
            CampoMapeo campo = tablaMapeo.getCampos().get(i);
            campos.add(campo.getNombre());
        }

        return campos;
    }    
    
    /**
     * Lista todos los campos de la pk de una tabla
     * @return campos un ArrayList<String> con los campos de la pk de la tabla
     */
    public ArrayList<String> listaCamposPkParaQuery() {
    	ArrayList<String> camposPk = new ArrayList<String>();
    	
        int cantidadCampos = tablaMapeo.getPrimaryKeyMapeo().getCampos().size();

        for (int i = 0; i < cantidadCampos; i++) {
        	CampoMapeo campoPk = tablaMapeo.getPrimaryKeyMapeo().getCampos().get(i);
        	camposPk.add(campoPk.getNombre());
        }
        
    	return camposPk;
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((tablaMapeo == null) ? 0 : tablaMapeo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NavajaDAO other = (NavajaDAO) obj;
		if (tablaMapeo == null) {
			if (other.tablaMapeo != null)
				return false;
		} else if (!tablaMapeo.equals(other.tablaMapeo))
			return false;
		return true;
	}

}
