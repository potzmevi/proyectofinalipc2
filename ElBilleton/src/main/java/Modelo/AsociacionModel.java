/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Conexion.Conexion;
import Objeto.Asociacion;
import Objeto.Cuenta;
import Objeto.Solicitud;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author potz
 */
public class AsociacionModel {
    
    private final String COMPROBAR_ASOCIACION = "SELECT S.codigo FROM ASOCIACION A INNER JOIN SOLICITUD S ON S.codigo=A.solicitud_codigo WHERE S.cuenta_codigoenvio=? && S.cueanta_codigorecibe=?";
    private final String CREAR_ASOCIACION = "INSERT INTO " + Asociacion.ASOCIACION_DB_NAME + " (" + Asociacion.FECHA_DB_NAME + ","
            + Asociacion.SOLICITUD_CODIGO_DB_NAME + ") VALUES (?,?)";
    
    
    private final String OBTENER_ASOCIACION_CUENTA_ENVIA = "SELECT * FROM " + Asociacion.ASOCIACION_DB_NAME + " A INNER JOIN " + Solicitud.SOLICITUD_DB_NAME + " S ON S." + Solicitud.CODIGO_DB_NAME + "=A." + Asociacion.SOLICITUD_CODIGO_DB_NAME + " WHERE S." + Solicitud.CUENTA_ENVIA_CODIGO_DB_NAME + "=?";
    private final String OBTENER_ASOCIACION_CUENTA_RECIBE = "SELECT * FROM " + Asociacion.ASOCIACION_DB_NAME + " A INNER JOIN " + Solicitud.SOLICITUD_DB_NAME + " S ON S." + Solicitud.CODIGO_DB_NAME + "=A." + Asociacion.SOLICITUD_CODIGO_DB_NAME + " WHERE S." + Solicitud.CUENTA_RECIBE_CODIGO_DB_NAME + "=?";
    
    /**
     * Comprobamos si la asociacion existe. De no
     * existir la nos devuelve un valor false.
     *
     * @param codigoEnvia
     * @param codigoRecibe
     * @return
     * @throws SQLException
     */
    public Boolean comprobarAsociacion(Long codigoEnvia, Long codigoRecibe) throws SQLException {
        PreparedStatement preSt = Conexion.getConnection().prepareStatement(COMPROBAR_ASOCIACION);
        preSt.setLong(1, codigoEnvia);
        preSt.setLong(2, codigoRecibe);
        int codigoEncontrado = 0;
        ResultSet result = preSt.executeQuery();
        Solicitud solicitud = null;
        while (result.next()) {

            return true;
        }
        return false;
    }

    
    /**
     * Agregamos una nueva asociacion y al completar la insercion devuelve el
     * codigo autogenerado de la asociacioon. De no existir nos devolvera
     * <code>-1</code>.
     *
     * @param asociacion
     * @return
     * @throws SQLException
     */
    public long crearAsociacion(Asociacion asociacion) throws SQLException {
        try {
            PreparedStatement preSt = Conexion.getConnection().prepareStatement(CREAR_ASOCIACION, Statement.RETURN_GENERATED_KEYS);

            preSt.setDate(1, asociacion.getFecha());
            preSt.setInt(2, asociacion.getSolicitud_codigo());
            preSt.executeUpdate();
            ResultSet result = preSt.getGeneratedKeys();
            if (result.first()) {
                return result.getLong(1);
            }
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }
    
    public ArrayList obtenerAsociacionesEnvia(long cuenta_codigo) {
        try {
            PreparedStatement preSt = Conexion.getConnection().prepareStatement(OBTENER_ASOCIACION_CUENTA_ENVIA);
            preSt.setLong(1, cuenta_codigo);
            ResultSet result = preSt.executeQuery();

            ArrayList codigosCuenta = new ArrayList();

             while (result.next()) {
                  codigosCuenta.add(result.getLong(Solicitud.CUENTA_RECIBE_CODIGO_DB_NAME));
             }
             return codigosCuenta;

        } catch (Exception e) {
            System.out.println("Error a al obtener las asocaciones cuando envia");
            return null;
        }

    }
    
    public ArrayList obtenerAsociacionesRecibe(long cuenta_codigo) {
        try {
            PreparedStatement preSt = Conexion.getConnection().prepareStatement(OBTENER_ASOCIACION_CUENTA_RECIBE);
            preSt.setLong(1, cuenta_codigo);
            ResultSet result = preSt.executeQuery();

            ArrayList codigosCuenta = new ArrayList();

             while (result.next()) {
                  codigosCuenta.add(result.getLong(Solicitud.CUENTA_ENVIA_CODIGO_DB_NAME));
             }
             return codigosCuenta;

        } catch (Exception e) {
            System.out.println("Error a al obtener las asocaciones cuando recibe");
            return null;
        }

    }

    
    
}
