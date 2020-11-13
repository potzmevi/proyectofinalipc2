/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Conexion.Conexion;
import Objeto.Gerente;
import Objeto.HIstorial_Gerente;
import Objeto.Historial_Cliente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import javax.swing.JOptionPane;

/**
 *
 * @author potz
 */
public class GerenteModel {
    
    public static final String BUSCAR_USUARIO = "Select * FROM " + Gerente.GERENTE_DB_NAME;
    private final String CREAR_USUARIO = "INSERT INTO " + Gerente.GERENTE_DB_NAME + " (" + Gerente.NOMBRE_DB_NAME + "," + Gerente.TURNO_DB_NAME + "," + Gerente.DPI_DB_NAME + "," + Gerente.DIRECCION_DB_NAME + "," + Gerente.SEXO_DB_NAME + "," + Gerente.PASSWORD_DB_NAME + ") VALUES (?,?,?,?,?,?)";
    private final String CREAR_USUARIO_CODIGO = "INSERT INTO " + Gerente.GERENTE_DB_NAME + " (" + Gerente.CODIGO_DB_NAME + "," + Gerente.NOMBRE_DB_NAME + "," + Gerente.TURNO_DB_NAME + "," + Gerente.DPI_DB_NAME + "," + Gerente.DIRECCION_DB_NAME + "," + Gerente.SEXO_DB_NAME + "," + Gerente.PASSWORD_DB_NAME + ") VALUES (?,?,?,?,?,?,?)";

    /**
     * Agregamos una nuevo usuario. Al completar la insercion devuelve el ID
     * autogenerado del usuario. De no existir nos devolvera <code>-1</code>.
     *
     * @param usuario
     * @return
     * @throws SQLException
     */
    public long agregarGerente(Gerente cajero) throws SQLException {
        
        PreparedStatement preSt = Conexion.getConnection().prepareStatement(CREAR_USUARIO, Statement.RETURN_GENERATED_KEYS);
        
        preSt.setString(1, cajero.getNombre());
        preSt.setString(2, cajero.getTurno());
        preSt.setString(3, cajero.getDPI());
        preSt.setString(4, cajero.getDireccion());
        preSt.setString(5, cajero.getSexo());
        preSt.setString(6, cajero.getPassword());
        
        preSt.executeUpdate();
        
        ResultSet result = preSt.getGeneratedKeys();
        if (result.first()) {
            return result.getLong(1);
        }
        
        return -1;
    }
    
    public long agregarGerenteCodigo(Gerente cajero) throws SQLException {
        PreparedStatement preSt = Conexion.getConnection().prepareStatement(CREAR_USUARIO_CODIGO, Statement.RETURN_GENERATED_KEYS);
        
        preSt.setLong(1, cajero.getCodigo());
        preSt.setString(2, cajero.getNombre());
        preSt.setString(3, cajero.getTurno());
        preSt.setString(4, cajero.getDPI());
        preSt.setString(5, cajero.getDireccion());
        preSt.setString(6, cajero.getSexo());
        preSt.setString(7, cajero.getPassword());
        
        Historial_GerenteModel hist = new Historial_GerenteModel();

        hist.agregarHistorialGerente(cajero);
        preSt.executeUpdate();
        
        ResultSet result = preSt.getGeneratedKeys();
        if (result.first()) {
            return result.getLong(1);
        }
        
        return -1;
    }

    /**
     * Verifiva si existen las credenciales y si son correctas en el usuario
     *
     * @param id
     * @param pass
     * @return
     * @throws SQLException
     */
    public Gerente loginValidation(Long id, String pass) throws SQLException {
        Gerente cliente = obtenerCliente(id);
        
        if (cliente != null && cliente.getPassword().equals(pass)) {
             
            return cliente;
                   
            
        }
        return null;
    }

    /**
     * Realizamos una busqueda en base al id del usuario. De no existir la nota
     * nos devuelve un valor null.
     *
     * @param idUsuario
     * @return
     * @throws SQLException
     */
    public Gerente obtenerCliente(Long idUsuario) throws SQLException {
        PreparedStatement preSt = Conexion.getConnection().prepareStatement(BUSCAR_USUARIO+" WHERE codigo='"+idUsuario+"'");
        ResultSet result = preSt.executeQuery();
        
        Gerente gerente = null;
        
        while (result.next()) {
            
            gerente = new Gerente(
                    
                    result.getLong(gerente.CODIGO_DB_NAME),
                    result.getString(gerente.NOMBRE_DB_NAME),
                    result.getString(gerente.TURNO_DB_NAME),
                    result.getString(gerente.DPI_DB_NAME),
                    result.getString(gerente.DIRECCION_DB_NAME),
                    result.getString(gerente.SEXO_DB_NAME),
                    result.getString(gerente.PASSWORD_DB_NAME)
            );
        }
        return gerente;
    }
    
    public Boolean estaEnTurno(Gerente gerente) {
        boolean si = true;
        String turno = gerente.getTurno();
        LocalTime hora = LocalTime.now();
        LocalTime horamatutina1 = LocalTime.of(7, 0);
        LocalTime horamatutina2 = LocalTime.of(14, 30);
        LocalTime vespertino1 = LocalTime.of(13, 0);
        LocalTime vespertino2 = LocalTime.of(22, 0);
        if (hora.isAfter(horamatutina1) && hora.isBefore(horamatutina2) && turno.equals("Matutino")) {
            si = true;
            return si;
            
        } else if (hora.isAfter(vespertino1) && hora.isBefore(vespertino2) && turno.equals("Vespertino")) {
            si = true;
            return si;
        } else {
            si = false;
            return si;
        }
    }
}
