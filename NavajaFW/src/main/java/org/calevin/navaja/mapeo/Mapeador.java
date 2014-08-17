package org.calevin.navaja.mapeo;

import java.io.IOException;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.calevin.navaja.excepciones.mapeo.MapeoCampoRepetidoException;
import org.calevin.navaja.excepciones.mapeo.MapeoClaseRepetidaException;
import org.calevin.navaja.excepciones.mapeo.MapeoException;
import org.calevin.navaja.excepciones.mapeo.MapeoTablaRepetidaException;
import org.calevin.navaja.util.NavajaConstantes;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Clase que abstrae el mapeo de la app
 * 
 * @author calevin
 * 
 */
public class Mapeador {

	private static RaizMapeo mapeoRaiz = null;
	private static String msjErrorMapeoNoRealizado ="Mapeo no realizado, el archivo no es un mapeo correcto";
	//TODO verificar tipo
	/*
    static final Map<String, Integer> TIPOS_DE_DATO = new HashMap<String, Integer>() {

        {
            put("VARCHAR", 1);
            put("INTEGER", 2);
            put("FLOAT", 3);
            put("DATE", 4);
        }
    };
	*/    
	
	public static RaizMapeo getMapeoRaiz() {
		return mapeoRaiz;
	}

	public static void limpiarMapeo() {
		mapeoRaiz = null;
	}
	
	/**
	 * Mapea el xml guardando el mapeo en la instancia
	 * 
	 * @param xmlName
	 *            nombre URI del xml a mapear
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 */
	public static void mapearXml(String xmlName) throws MapeoException {

				// Creo un parser 
				SAXParser parser = null;

				try {
					parser = SAXParserFactory.newInstance().newSAXParser();

					// Parseo el archivo; el segundo parametro es una clase interna
					// que necesita SAX
					parser.parse(xmlName, new LectorSax());

				} catch (ParserConfigurationException e) {
					throw new MapeoException(e.getMessage());
				} catch (MapeoCampoRepetidoException e) {
					throw e;
				} catch (MapeoTablaRepetidaException e) {
					throw e;
				} catch (MapeoClaseRepetidaException e) {
					throw e;
				} catch (SAXException e) {
					throw new MapeoException(e.getMessage());
				} catch (IOException e) {
					throw new MapeoException(e.getMessage());
				}
				if (mapeoRaiz == null || mapeoRaiz.getTablas().isEmpty()) {
					throw new MapeoException(msjErrorMapeoNoRealizado);
				}
	}

	private static Boolean isTablaRepetida(String nombreTabla) {
		boolean bandera = false;

		Iterator<TablaMapeo> tablas = mapeoRaiz.getTablas().iterator();

		while (tablas.hasNext() && bandera == false) {
			TablaMapeo tabla = (TablaMapeo) tablas.next();
			bandera = (nombreTabla.equals(tabla.getNombre()));
		}

		return bandera;
	}

	private static Boolean isClaseRepetida(String nombreNuevaClase) {
		boolean bandera = false;

		Iterator<String> nombresDeClase = mapeoRaiz.getNombresClase()
				.iterator();

		while (nombresDeClase.hasNext() && bandera == false) {
			String nombreClase = (String) nombresDeClase.next();
			bandera = (nombreNuevaClase.equals(nombreClase));
		}

		return bandera;
	}

	private static Boolean isCampoRepetido(TablaMapeo tabla, String nombreCampo) {
		boolean bandera = false;

		Iterator<CampoMapeo> campos = tabla.getCampos().iterator();

		while (campos.hasNext() && bandera == false){
			CampoMapeo campoMapeo = (CampoMapeo) campos.next();
			bandera = (nombreCampo.equals(campoMapeo.getNombre()));
		}
				
		return bandera;
	}

	public static class LectorSax extends DefaultHandler {

		private String RAIZMAPEO = "raiz-mapeo";
		private String PRIMARYKEY = "primary-key";
		private String NOMBRECOMOATRIBUTO = "nombre-como-atributo";
		
		private boolean esPk = false;

		/**
		 * Cada vez que se abre un elemento del xml se ejecuta este metodo
		 * Existe un objeto para cada elemento del xml, los objetos q se van
		 * componiendo a medida que se lee el xml
		 * 
		 * @param uri
		 * @param localName
		 * @param elementName
		 * @param attributes
		 * @throws MapeoClaseRepetidaException 
		 * @throws SAXException
		 */
		@Override
		public void startElement(String uri, String localName,
				String elementName, Attributes attributes) throws MapeoCampoRepetidoException
				, MapeoTablaRepetidaException, MapeoClaseRepetidaException{
			// Si el elemento abierto es "mapeo-raiz" es la raiz del archivo
			// *-_or.xml
			// Se lo seteo a la clase estatica mapeoRaiz del Mapeador
			if (elementName.equals(RAIZMAPEO) && (mapeoRaiz == null)) {
				mapeoRaiz = new RaizMapeo();
			}

			// Si el elemento es "tabla"
			if (elementName.equals(NavajaConstantes.TABLA)) {

				// Tomo el valor del atributo "nombre"
				String nombreTabla = attributes.getValue(NavajaConstantes.NOMBRE);

				// Si la tabla no esta repetida
				if (!isTablaRepetida(nombreTabla)) {
					// instancio la tabla
					TablaMapeo tabla = new TablaMapeo();

					// Le seteo su atributo nombre
					tabla.setNombre(nombreTabla);

					String nombreClase = attributes
							.getValue(NavajaConstantes.CLASE);
					// SI la clase no esta repetida
					if (!isClaseRepetida(nombreClase)) {
						// Le seteo su atributo
						tabla.setNombreComoClase(nombreClase);

						// Agrego el nombre la lista de nombres de clase
						mapeoRaiz.setNombreClase(nombreClase);

						// Agrego la tabla a las tablas del mapeo
						mapeoRaiz.getTablas().add(0, tabla);
					} else {
						throw new MapeoClaseRepetidaException(nombreClase);
					}

				} else {
					throw new MapeoTablaRepetidaException(nombreTabla);
				}

			}// Fin de si el elemento es "tabla"

			// Si el elemento es una PK, seteo la bandera en true
			// Instacio la PK, se la seteo a esta tabla de las tablas
			// del Mapeador
			if (elementName.equals(PRIMARYKEY)) {
				esPk = true;
				PrimaryKeyMapeo pk = new PrimaryKeyMapeo();
				mapeoRaiz.getTablas().get(0).setPrimaryKeyMapeo(pk);

			} else if (elementName.equals(NavajaConstantes.CAMPO)) {
				// Si no es una PK y es un CampoMapeo
				String nombreCampo = attributes
						.getValue(NavajaConstantes.NOMBRE);

				if (!isCampoRepetido(mapeoRaiz.getTablas().get(0), nombreCampo)) {
	                //instancio un Campo
	                //Le seteo el nombre
                    CampoMapeo campo = new CampoMapeo();
                    campo.setNombre(nombreCampo);
                    
                    //Si era pk, agrego ese campo a la pk                
                    if (esPk) {
                        PrimaryKeyMapeo pk = mapeoRaiz.getTablas().get(0).getPrimaryKeyMapeo();
                        pk.getCampos().add(0, campo);

                    } else {
                        //Si no era pk tomo esta tabla y le seteo este campo
                        mapeoRaiz.getTablas().get(0).getCampos().add(0, campo);
                        campo.setNombreComoAtributo(attributes.getValue(NOMBRECOMOATRIBUTO));
                        //TODO revisar tipo
                        //campo.setTipo(_setearTipo(attributes.getValue("tipo")));
                    }
                    
                } else {
                    throw new MapeoCampoRepetidoException(nombreCampo);
                }

			}//Fin de elemento xml igual campo
		}//Fin metodo startElement

	     /**
         * Se ejecuta al finalizar un elemento del xml, 
         * si el elemento que termina es "pk" cambia la bandera esPK a false
         *
         * @param uri
         * @param localName
         * @param elementName
         * @throws SAXException
         */
        @Override
        public void endElement(String uri, String localName, String elementName)
                throws SAXException {
            if (elementName.equals(PRIMARYKEY)) {
                esPk = false;
            }
        }
		
	}// Fin de la clase interna LectorSax

} // Fin de la clase Mapeador
