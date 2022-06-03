package org.service.crud;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.jws.WebMethod;
import javax.jws.WebService;

/*
 * WEB SERVICE SOAP:
 * 
 * WSDL: WEB SERVICE DESCRIPTION LANGUAJE, ES EL LENGUAJE ESPECIFICADO DEL WEB SERVICE.
 * 
 * UDDI: UNIVERSAL DESCRIPTION AND DISCOVERY INTEGRATION, PERMITE BUSCAR Y DESCUBRIR
 * WEB SERVICES (PUBLIC, PRIVATE).
 * 
 * SOAP: SIMPLE OBJECT ACCESS PROTOCOL, PROTOCOLO DE DATOS QUE EMPLEA EL WEB SERVICE SOAP
 * ES DEL TIPO XML.
 * 
 * ...................................................................................
 * 
 * REGLAS DE CREACIÓN DE WEB SERVICE SOAP
 * 
 * 1.- TODOS LOS METODOS DEBEN COMENZAR CON MÍNUSCULA, Y NO SE PUEDEN REESTRUCTURAR.
 * 
 * 2.- AGREGAR LAS ANOTACIONES: @WebService, @WebMethod
 * 
 * ....................................................................................
 * 
 * SI SE DESEA HACER ALGUN CAMBIO, UNA VEZ YA CREADO EL WEB SERVICE.
 * 
 * 1.- STOP SERVER APACHE...
 * 
 * 2.- ADD AND REMOVE - REMOVER EL PROYECTO DEL SERVIDOR Y LIMPIAR CLEAN
 * 
 * 3.- WEBCONTENT - WSDL (ELIMINAR LA CARPETA)
 * 
 * 4.- VOLVER A CREAR EL WEB SERVICE SOAP
 * 
 * .....................................................................................
 *                                        REMOTAMENTE
 * WEB SERVICE SOAP: WEB SERVICE SERVIDOR -----------> WEB SERVICE CLIENTE
 *                    CLASS JAVA SERVICE                     JSP´S
 * 
 * .....................................................................................
 * 
 * NOTA: ***IMPORTANTE GUARDAR LA CLASE ANTES DE GENERAR EL WEB SERVICE SOAP***
 *    
 */

@WebService
public class ServiceCrudPersona {

	static Connection connection = null;
	static String driver = "oracle.jdbc.driver.OracleDriver";
	static String url = "jdbc:oracle:thin:@localhost:1521:orcl";

	@WebMethod
	public static void connecDataBaseOracle() throws IOException {
		try {
			Class.forName(driver).newInstance();
			System.out.println("Cargo Driver");
		} catch (Exception e) {
			System.out.println("No se pudo cargar el driver Oracle");
		}

		try {
			connection = DriverManager.getConnection(url, "system", "Astrix12");
			System.out.println("Conexion establecida.");

		} catch (SQLException sqle) {

			System.out.println("El nombre de usuario o contraseña no son validos:");

		}
		return;
	}

	@WebMethod
	public static String altaPersona(int id, String nombre, String apepat, String tel) 
	throws SQLException, IOException {
		connecDataBaseOracle();
		try {
		String sql = "INSERT INTO PERSONABATCH (ID,NOMBRE,APEPAT,TEL) VALUES (?,?,?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, id);
		ps.setString(2, nombre);
		ps.setString(3, apepat);
		ps.setString(4, tel);
		ps.executeQuery();
		} catch (Exception e) {
			System.out.println("ERROR: " + e);
		}
		return "REALIZO CORRECTAMENTE EL ALTA DE LA PERSONA.";
	}

	@WebMethod
	public static String modificaPersona(String nombre, String apepat, String tel,int id) 
			throws SQLException, IOException {
				connecDataBaseOracle();
				try {
				String sql = "UPDATE PERSONABATCH SET NOMBRE = ?, APEPAT = ?, "
						+ "TEL = ? WHERE ID = ?";
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setString(1, nombre);
				ps.setString(2, apepat);
				ps.setString(3, tel);
				ps.setInt(4, id);
				ps.executeQuery();
				} catch (Exception e) {
					System.out.println("ERROR: " + e);
				}
				return "REALIZO CORRECTAMENTE LA MODIFICACIÓN DE LA PERSONA.";
	}
	
	@WebMethod
	public static String eliminaPersona(int id) throws SQLException, IOException {
				connecDataBaseOracle();
				try {
				String sql = "DELETE FROM PERSONABATCH WHERE ID = ?";
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setInt(1, id);
				ps.executeQuery();
				} catch (Exception e) {
					System.out.println("ERROR: " + e);
				}
				return "REALIZO CORRECTAMENTE LA MODIFICACIÓN DE LA PERSONA.";
	}
	
	@WebMethod
	public static String consultaIdPersona(int id) throws SQLException, IOException {
		connecDataBaseOracle();
		String nombre = null;
		String apepat = null;
		String tel = null;
		try {
		String sql = "SELECT * FROM PERSONABATCH WHERE ID = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				nombre = rs.getString("nombre");
				apepat = rs.getString("apepat");
				tel = rs.getString("tel");
			}
		} catch (Exception e) {
			System.out.println("ERROR: " + e);
		}
		return "DATO CONSULTADO:" + id + "," + nombre + "," + apepat + "," + tel;
}
	
}
