/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Conexion.Conexion;
import Encriptar.Encriptar;
import Objeto.Cajero;
import Objeto.Gerente;
import Objeto.Transaccion;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author potz
 */
public class CajeroModel {

    public static final String BUSCAR_USUARIO = "Select * FROM " + Cajero.CAJERO_DB_NAME;
    private final String CREAR_USUARIO = "INSERT INTO " + Cajero.CAJERO_DB_NAME + " (" + Cajero.NOMBRE_DB_NAME + "," + Cajero.TURNO_DB_NAME + "," + Cajero.DPI_DB_NAME + "," + Cajero.DIRECCION_DB_NAME + "," + Cajero.SEXO_DB_NAME + "," + Cajero.PASSWORD_DB_NAME + ") VALUES (?,?,?,?,?,?)";
    private final String CREAR_USUARIO_CODIGO = "INSERT INTO " + Cajero.CAJERO_DB_NAME + " (" + Cajero.CODIGO_DB_NAME + "," + Cajero.NOMBRE_DB_NAME + "," + Cajero.TURNO_DB_NAME + "," + Cajero.DPI_DB_NAME + "," + Cajero.DIRECCION_DB_NAME + "," + Cajero.SEXO_DB_NAME + "," + Cajero.PASSWORD_DB_NAME + ") VALUES (?,?,?,?,?,?,?)";
    private final String EDITAR_CAJERO = "UPDATE " + Cajero.CAJERO_DB_NAME + " SET " + Cajero.NOMBRE_DB_NAME + "=?,"
            + Cajero.TURNO_DB_NAME + "=?," + Cajero.DPI_DB_NAME + "=?," + Cajero.DIRECCION_DB_NAME + "=?," + Cajero.SEXO_DB_NAME + "=?,"
            + Cajero.PASSWORD_DB_NAME + "=? WHERE " + Cajero.CODIGO_DB_NAME + " =?";

    private final String REPORTE_7 = "SELECT COUNT(*) AS transacciones,C.* FROM " + Cajero.CAJERO_DB_NAME + " C INNER JOIN " + Transaccion.TRANSACCION_DB_NAME + " T ON C.codigo=T.cajero_codigo WHERE T.fecha BETWEEN ? AND ? && C.codigo!=101 GROUP BY C.codigo ORDER BY transacciones DESC LIMIT 1";

    /**
     * Agregamos una nuevo usuario. Al completar la insercion devuelve el ID
     * autogenerado del usuario. De no existir nos devolvera <code>-1</code>.
     *
     * @param usuario
     * @return
     * @throws SQLException
     */
    public long agregarCajero(Cajero cajero) throws SQLException, UnsupportedEncodingException {
        try {
            PreparedStatement preSt = Conexion.getConnection().prepareStatement(CREAR_USUARIO, Statement.RETURN_GENERATED_KEYS);

            preSt.setString(1, cajero.getNombre());
            preSt.setString(2, cajero.getTurno());
            preSt.setString(3, cajero.getDPI());
            preSt.setString(4, cajero.getDireccion());
            preSt.setString(5, cajero.getSexo());
            preSt.setString(6, Encriptar.encriptar(cajero.getPassword()));

            preSt.executeUpdate();

            ResultSet result = preSt.getGeneratedKeys();
            if (result.first()) {
                return result.getLong(1);
            }
        } catch (SQLException e) {

        }

        return -1;
    }

    public long agregarCajeroCodigo(Cajero cajero) throws SQLException, UnsupportedEncodingException {
        try {
            PreparedStatement preSt = Conexion.getConnection().prepareStatement(CREAR_USUARIO_CODIGO, Statement.RETURN_GENERATED_KEYS);

            preSt.setLong(1, cajero.getCodigo());
            preSt.setString(2, cajero.getNombre());
            preSt.setString(3, cajero.getTurno());
            preSt.setString(4, cajero.getDPI());
            preSt.setString(5, cajero.getDireccion());
            preSt.setString(6, cajero.getSexo());
            preSt.setString(7, Encriptar.encriptar(cajero.getPassword()));
            preSt.executeUpdate();
            Historial_CajeroModel hist = new Historial_CajeroModel();
            hist.agregarCajero(cajero);
            ResultSet result = preSt.getGeneratedKeys();
            if (result.first()) {
                return result.getLong(1);
            }
        } catch (SQLException e) {
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
    public Cajero loginValidation(Long id, String pass) throws SQLException, UnsupportedEncodingException {
        Cajero cajero = obtenerCliente(id);
        if (cajero != null && cajero.getPassword().equals(pass)) {
            return cajero;

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
    public Cajero obtenerCliente(Long idUsuario) throws SQLException, UnsupportedEncodingException {
        PreparedStatement preSt = Conexion.getConnection().prepareStatement(BUSCAR_USUARIO + " WHERE codigo='" + idUsuario + "'");
        ResultSet result = preSt.executeQuery();

        Cajero cajero = null;

        while (result.next()) {
            cajero = new Cajero(
                    result.getLong(cajero.CODIGO_DB_NAME),
                    result.getString(cajero.NOMBRE_DB_NAME),
                    result.getString(cajero.TURNO_DB_NAME),
                    result.getString(cajero.DPI_DB_NAME),
                    result.getString(cajero.DIRECCION_DB_NAME),
                    result.getString(cajero.SEXO_DB_NAME),
                    result.getString(cajero.PASSWORD_DB_NAME)
            );
        }
        cajero.setPassword(Encriptar.desencriptar(cajero.getPassword()));
        return cajero;
    }

    public long modificarCajero(Cajero cajero) throws SQLException, UnsupportedEncodingException {
        try {
            PreparedStatement preSt = Conexion.getConnection().prepareStatement(EDITAR_CAJERO, Statement.RETURN_GENERATED_KEYS);

            preSt.setString(1, cajero.getNombre());
            preSt.setString(2, cajero.getTurno());
            preSt.setString(3, cajero.getDPI());
            preSt.setString(4, cajero.getDireccion());
            preSt.setString(5, cajero.getSexo());
            preSt.setString(6, Encriptar.encriptar(cajero.getPassword()));
            preSt.setLong(7, cajero.getCodigo());

            preSt.executeUpdate();

        } catch (SQLException e) {
           
        }

        return -1;
    }

    public ArrayList obtenerCajeros(String idUsuario) throws SQLException, UnsupportedEncodingException {
        PreparedStatement preSt = Conexion.getConnection().prepareStatement(BUSCAR_USUARIO + " WHERE codigo LIKE '%" + idUsuario + "%' && CAJERO.codigo!=101");
        ResultSet result = preSt.executeQuery();
        ArrayList listaGerentes = new ArrayList();
        Cajero gerente = null;
        while (result.next()) {
            gerente = new Cajero(
                    result.getLong(gerente.CODIGO_DB_NAME),
                    result.getString(gerente.NOMBRE_DB_NAME),
                    result.getString(gerente.TURNO_DB_NAME),
                    result.getString(gerente.DPI_DB_NAME),
                    result.getString(gerente.DIRECCION_DB_NAME),
                    result.getString(gerente.SEXO_DB_NAME),
                    result.getString(gerente.PASSWORD_DB_NAME)
            );
            gerente.setPassword(Encriptar.desencriptar(gerente.getPassword()));
            listaGerentes.add(gerente);
        }
        return listaGerentes;

    }

    public ArrayList obtenerCajerosReporte7(Date fecha1, Date fecha2) throws SQLException, UnsupportedEncodingException {

        PreparedStatement preSt = Conexion.getConnection().prepareStatement(REPORTE_7);
        preSt.setDate(1, fecha1);
        preSt.setDate(2, fecha2);
        ResultSet result = preSt.executeQuery();
        ArrayList listaGerentes = new ArrayList();
        Cajero gerente = null;

        while (result.next()) {
            gerente = new Cajero(
                    result.getLong(gerente.CODIGO_DB_NAME),
                    result.getString(gerente.NOMBRE_DB_NAME),
                    result.getString(gerente.TURNO_DB_NAME),
                    result.getString(gerente.DPI_DB_NAME),
                    result.getString(gerente.DIRECCION_DB_NAME),
                    result.getString(gerente.SEXO_DB_NAME),
                    result.getString("transacciones")
            );
            listaGerentes.add(gerente);
        }
        return listaGerentes;

    }

    public Boolean estaEnTurno(Cajero gerente) {
        boolean si = true;
        String turno = gerente.getTurno();
        LocalTime hora = LocalTime.now();
        LocalTime horamatutina1 = LocalTime.of(6, 0);
        LocalTime horamatutina2 = LocalTime.of(14, 30);
        LocalTime vespertino1 = LocalTime.of(13, 0);
        LocalTime vespertino2 = LocalTime.of(22, 0);
        if (hora.isAfter(horamatutina1) && hora.isBefore(horamatutina2) && turno.equalsIgnoreCase("Matutino")) {
            si = true;
            return si;

        } else if (hora.isAfter(vespertino1) && hora.isBefore(vespertino2) && turno.equalsIgnoreCase("Vespertino")) {
            si = true;
            return si;
        } else {
            si = false;
            return si;
        }
    }
}
