/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.parametrossigep.dao.impl;

import co.edu.udea.parametrossigep.dao.ActividadDAO;
import co.edu.udea.parametrossigep.dao.cnf.JDBCConnectionPool;
import co.edu.udea.parametrossigep.dto.Actividad;
import co.edu.udea.parametrossigep.exception.GIDaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jorge.correa
 */
public class ActividadDAOImpl extends  JDBCConnectionPool implements ActividadDAO{
    
    private static final String OBTENER_UNA = "SELECT * FROM sigap.sigap_actividades WHERE Codigo = ?";
    private static final String OBTENER_TODAS = "SELECT * FROM sigap.sigap_actividades";
    private static final String OBTENER_POR_FECHA_IGUAL = "SELECT * FROM sigap.sigap_actividades WHERE Fin = ?";
    private static final String OBTENER_POR_FECHA_MENOR = "SELECT * FROM sigap.sigap_actividades WHERE Fin <= ?";
    private static final String OBTENER_POR_FECHA_MAYOR = "SELECT * FROM sigap.sigap_actividades WHERE Fin >= ?";
    private static final String COLUMNA_CODIGO = "Codigo";
    private static final String COLUMNA_ETAPA = "Etapa";
    private static final String COLUMNA_PROYECTO = "Proyecto";
    private static final String COLUMNA_DESCRIPCION = "Descripcion";
    private static final String COLUMNA_FECHA_INICIO = "Inicio";
    private static final String COLUMNA_FECHA_FIN = "Fin";
    private static final String COLUMNA_OBSERVACION = "Observacion";
    private static final String COLUMNA_PRODUCTO = "Producto";

    @Override
    public Actividad obtenerPorCodigo(Integer strIdActividad) throws GIDaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Actividad actividad = null;
        
        try{
            con = getConexion();
            ps = con.prepareCall(OBTENER_UNA);
            ps.setInt(1, strIdActividad);
            
            rs = ps.executeQuery();
            
            if (rs.next()){           
                   actividad = new Actividad();                    
                   actividad.setCodigo(rs.getInt(COLUMNA_CODIGO));
                   actividad.setEtapa(rs.getInt(COLUMNA_ETAPA));
                   actividad.setProyecto(rs.getString(COLUMNA_PROYECTO));
                   actividad.setDescripcion(rs.getString(COLUMNA_DESCRIPCION));
                   actividad.setFechaInicio(rs.getDate(COLUMNA_FECHA_INICIO));
                   actividad.setFechaFin(rs.getDate(COLUMNA_FECHA_FIN));
                   actividad.setObservacion(rs.getString(COLUMNA_OBSERVACION));
                   actividad.setProducto(rs.getString(COLUMNA_PRODUCTO));                   
            }
            
        }catch(SQLException e){
            throw new GIDaoException(e);
        }finally{
            try{
                
                if (rs != null){
                    rs.close();
                }
                
                 if (ps != null){
                    ps.close();
                }
                 
                  if (con != null){
                    con.close();
                }
                  
            }catch(SQLException e){
                throw new GIDaoException(e);
            }
        }
        
        return actividad;
    }

    @Override
    public List<Actividad> obtenerTodas() throws GIDaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Actividad> actividades = new ArrayList<Actividad>();
        Actividad actividad = null;
        
        try{
            con = getConexion();
            ps = con.prepareCall(OBTENER_TODAS);
            
            rs = ps.executeQuery();
            
            if (rs != null){
                while (rs.next()){
                   actividad = new Actividad();
                   actividad.setCodigo(rs.getInt(COLUMNA_CODIGO));
                   actividad.setEtapa(rs.getInt(COLUMNA_ETAPA));
                   actividad.setProyecto(rs.getString(COLUMNA_PROYECTO));
                   actividad.setDescripcion(rs.getString(COLUMNA_DESCRIPCION));
                   actividad.setFechaInicio(rs.getDate(COLUMNA_FECHA_INICIO));
                   actividad.setFechaFin(rs.getDate(COLUMNA_FECHA_FIN));
                   actividad.setObservacion(rs.getString(COLUMNA_OBSERVACION));
                   actividad.setProducto(rs.getString(COLUMNA_PRODUCTO));                    
                    actividades.add(actividad);
                }
            }
            
        }catch(SQLException e){
            throw new GIDaoException(e);
        }finally{
            try{
                
                if (rs != null){
                    rs.close();
                }
                
                 if (ps != null){
                    ps.close();
                }
                 
                  if (con != null){
                    con.close();
                }
                  
            }catch(SQLException e){
                throw new GIDaoException(e);
            }
        }
        
        return actividades;
    }

    @Override
    public List<Actividad> obtenerPorFecha(String strFechaBase, String strCriterio) throws GIDaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Actividad> actividades = new ArrayList<Actividad>();
        Actividad actividad = null;
        
        try{
            con = getConexion();
            
            if (strCriterio.equals("=")){
                ps = con.prepareCall(OBTENER_POR_FECHA_IGUAL);
            }
            
            if (strCriterio.equals(">")){
                ps = con.prepareCall(OBTENER_POR_FECHA_MAYOR);
            }
            
            if (strCriterio.equals("<")){
                ps = con.prepareCall(OBTENER_POR_FECHA_MENOR);
            }
            
            ps.setString(1, strFechaBase);
            rs = ps.executeQuery();
            
            if (rs != null){
                while (rs.next()){
                   actividad = new Actividad();
                   actividad.setCodigo(rs.getInt(COLUMNA_CODIGO));
                   actividad.setEtapa(rs.getInt(COLUMNA_ETAPA));
                   actividad.setProyecto(rs.getString(COLUMNA_PROYECTO));
                   actividad.setDescripcion(rs.getString(COLUMNA_DESCRIPCION));
                   actividad.setFechaInicio(rs.getDate(COLUMNA_FECHA_INICIO));
                   actividad.setFechaFin(rs.getDate(COLUMNA_FECHA_FIN));
                   actividad.setObservacion(rs.getString(COLUMNA_OBSERVACION));
                   actividad.setProducto(rs.getString(COLUMNA_PRODUCTO));                    
                    actividades.add(actividad);
                }
            }
            
        }catch(SQLException e){
            throw new GIDaoException(e);
        }finally{
            try{
                
                if (rs != null){
                    rs.close();
                }
                
                 if (ps != null){
                    ps.close();
                }
                 
                  if (con != null){
                    con.close();
                }
                  
            }catch(SQLException e){
                throw new GIDaoException(e);
            }
        }
        
        return actividades;
    }
    
}
