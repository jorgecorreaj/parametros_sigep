/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.parametrossigep.dao.impl;

import co.edu.udea.parametrossigep.dao.FuncionesComunesDAO;
import co.edu.udea.parametrossigep.dao.ParametroGeneralDAO;
import co.edu.udea.parametrossigep.dao.cnf.JDBCConnectionPool;
import co.edu.udea.parametrossigep.dto.ParametroGeneral;
import co.edu.udea.parametrossigep.exception.GIDaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jorge.correa
 */
public class ParametroGeneralDAOImpl extends JDBCConnectionPool implements ParametroGeneralDAO{
    
    private static final String OBTENER_PARAMETROS_GENERALES = "SELECT * FROM sigap.sigap_parametrosgenerales WHERE txtCodigo = ?";    
    private static final String CODIGO_FORM = "frmGeneral";
    private static final String COLUMNA_CODIGO = "txtCodigo";
    private static final String COLUMNA_NOMBRE_SERVIDOR = "txtNombreServidor";
    private static final String COLUMNA_NUMERO_PUERTO = "intNumeroPuerto";
    private static final String COLUMNA_USUARIO_CONEXION = "txtUsuario";
    private static final String COLUMNA_CLAVE_CONEXION = "txtPassword";
    private static final String COLUMNA_CUERPO_MENSAJE = "txtCuerpoMensaje";
    private static final String COLUMNA_FIRMA_MENSAJE = "txtFirmaMensaje";
    private static final String COLUMNA_MENSAJE_REGALIAS = "txtMensajeRegalias";
    private static final String COLUMNA_DIA_LIMITE_ENVIO_COLILLA = "txtDiaLimiteEnvioColilla";
    private static final String COLUMNA_NUMERO_DIAS_NOTIFICAR_ACTIVIDAD = "intNumDiasNotificarActividad";
    private static final String COLUMNA_MODO_PDN = "txtModoPdn";
            
    @Override
    public ParametroGeneral obtenerParametrosGenerales() throws GIDaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ParametroGeneral parametroGeneral = null;
        
        try{
            con = getConexion();
            ps = con.prepareCall(OBTENER_PARAMETROS_GENERALES);
            ps.setString(1, CODIGO_FORM);
            
            rs = ps.executeQuery();
            
            if (rs.next()){                
                    parametroGeneral = new ParametroGeneral();                    
                    
                    parametroGeneral.setCodigo(rs.getString(COLUMNA_CODIGO));         
                    parametroGeneral.setNombreServidor(rs.getString(COLUMNA_NOMBRE_SERVIDOR));
                    parametroGeneral.setNumeroPuerto(rs.getInt(COLUMNA_NUMERO_PUERTO));
                    parametroGeneral.setUsuarioConexion(rs.getString(COLUMNA_USUARIO_CONEXION));
                    parametroGeneral.setClaveConexion(rs.getString(COLUMNA_CLAVE_CONEXION));                    
                    parametroGeneral.setCuerpoMensaje(rs.getString(COLUMNA_CUERPO_MENSAJE));
                    parametroGeneral.setFirmaMensaje(rs.getString(COLUMNA_FIRMA_MENSAJE));
                    parametroGeneral.setMensajeRegalias(rs.getString(COLUMNA_MENSAJE_REGALIAS));
                    parametroGeneral.setDiaLimiteEnvioColilla(rs.getString(COLUMNA_DIA_LIMITE_ENVIO_COLILLA));
                    parametroGeneral.setNumDiasNotificarActividad(rs.getInt(COLUMNA_NUMERO_DIAS_NOTIFICAR_ACTIVIDAD));
                    parametroGeneral.setModoPDN(rs.getString(COLUMNA_MODO_PDN));
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
        
        return parametroGeneral;
    }

    @Override
    public Boolean verificarNotificacionProyecto(String strIdProyecto) throws GIDaoException {
        String strFechaActual=null, strFechaInicio=null, strFechaFin=null, strSQL=null, strCodigo;
        Boolean notificarProyecto = Boolean.TRUE;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        FuncionesComunesDAO funcionesComunesDAO = new FuncionesComunesDAOImpl();
        strFechaActual = funcionesComunesDAO.getFechaActual();        
        strFechaActual = strFechaActual.substring(0, 4);        
        strFechaInicio = strFechaActual + "-01-01";
        strFechaFin = strFechaActual + "-12-31";
        strCodigo = null;
        
        strSQL = "SELECT p.Codigo as txtCodigo  FROM sigap_proyectos p, sigap_etapas e WHERE (p.Codigo = e.Proyecto) and p.TipoProyectos = '11' and (e.Inicio >= '" + strFechaInicio + "' and e.Fin <= '" + strFechaFin + "') and p.Codigo = '" + strIdProyecto + "'";        
        
        try{
            con = getConexion();
            ps = con.prepareCall(strSQL);                                
            rs = ps.executeQuery();
            
            if (rs.next()){                
                   strCodigo =rs.getString(COLUMNA_CODIGO);
                   
                   if ((strCodigo != null) && (!(strCodigo.equals("")))){
                        System.out.println("Código exento: " + strIdProyecto);
                        notificarProyecto = Boolean.FALSE;
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
        
        return notificarProyecto;
    }
    
    
    /*public Boolean verificarNotificacionProyecto(String strIdProyecto) throws GIDaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Boolean notificarProyecto = Boolean.TRUE;
        String strCodigosExentos = null, strSQL=null;
        
        strIdProyecto = strIdProyecto.substring(2);          
        strSQL = "SELECT * FROM sigap.sigap_parametrosgenerales p WHERE p.txtProyectosExentosNotificacion like '%" + strIdProyecto + "%'";             
        
        try{
            con = getConexion();
            ps = con.prepareCall(strSQL);                                
            rs = ps.executeQuery();
            
            if (rs.next()){                
                    strCodigosExentos =rs.getString(COLUMNA_PROYECTOS_EXENTOS);
                    
                    if ((strCodigosExentos != null) && (!(strCodigosExentos.equals("")))){
                        notificarProyecto = Boolean.FALSE;
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
        
        return notificarProyecto;
    }*/
    
}
