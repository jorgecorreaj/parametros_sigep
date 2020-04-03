/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.parametrossigep.dao;

import co.edu.udea.parametrossigep.dto.Grupo;
import co.edu.udea.parametrossigep.exception.GIDaoException;

/**
 *
 * @author jorge.correa
 */
public interface GrupoDAO {
    public Grupo obtenerUno(Integer intCodigo) throws GIDaoException;
}
