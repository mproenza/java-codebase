/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.base.security.service;

import org.base.security.auth.AccionPermiso;
import org.base.security.auth.exception.DSecurityException;
import java.security.Permission;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.base.dao.datasources.context.DataSourceContext;
import org.base.dao.wrappers.ResultSetWrapper;
import org.base.security.auth.config.AuthEntryPoint;
import org.base.security.config.SecurityValues;

/**
 * Clase de acceso a datos
 * @author Luis Valdes Guerrero <lvaldes@grm.desoft.cu>
 */
public class DbServise {

    // <editor-fold defaultstate="collapsed" desc="DECLARACIÓN DE VARIABLES">
    DataSourceContext dataSourceContext = AuthEntryPoint.dataSourceContext;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTOR">
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="MÉTODOS">
    protected String getContextualDBObjectName(String objName) {
        return dataSourceContext.getDBObjectExpression(objName);
    }
    
    protected String getContextualFunctionName(String fnName) {
        return dataSourceContext.getDBObjectExpression(fnName);
    }
    
    protected Connection getContextualConnection() {
        return dataSourceContext.getConnection();
    }
    
    protected void closeContextualConnection(Connection conn) {
        dataSourceContext.close(conn);
    }
    
    protected void closeContextualStatement(Statement stm) {
        dataSourceContext.close(stm);
    }
    
    public List<Permission> permisosUsuario(String usuario) {
        List<Permission> list = new ArrayList<Permission>();
        Connection conn = null;
        Statement pstm = null;
        try {
            String stm;
            stm = "SELECT permiso.accion, permiso.recurso"
                    + " FROM " + getContextualDBObjectName(SecurityValues.TABLE_NAME_PERMISSION) + ", " + getContextualDBObjectName(SecurityValues.TABLE_NAME_USER_PERMISSION)
                    + " WHERE permiso.id = usuario_permiso.id_permiso"
                    + " and usuario_permiso.id_usuario = '" + usuario + "'";

            conn = dataSourceContext.getConnection();
            pstm = conn.createStatement();
            ResultSet result = pstm.executeQuery(stm);
            while (result.next()) {
                AccionPermiso p = new AccionPermiso(result.getString(2), result.getString(1));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger("carnico").log(Level.SEVERE, null, ex);
        } finally {
            //dataSourceContext.close(conn);
            closeContextualStatement(pstm);
            closeContextualConnection(conn);
        }
        return list;
    }

    public List<Permission> permisosRol(String usuario) {
        List<Permission> list = new ArrayList<Permission>();
        Connection conn = null;
        Statement pstm = null;
        try {
            String stm = "SELECT permiso.accion, permiso.recurso"
                    + " FROM " + getContextualDBObjectName(SecurityValues.TABLE_NAME_PERMISSION) + ", " + getContextualDBObjectName(SecurityValues.TABLE_NAME_ROL_PERMISSION)
                    + " WHERE permiso.id = rol_permiso.id_permiso"
                    + " and rol_permiso.id_rol = '" + usuario + "'";

            conn = dataSourceContext.getConnection();
            pstm = conn.createStatement();
            ResultSet result = pstm.executeQuery(stm);
            while (result.next()) {
                AccionPermiso p = new AccionPermiso(result.getString(2), result.getString(1));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger("carnico").log(Level.SEVERE, null, ex);
        } finally {
            //dataSourceContext.close(conn);
            closeContextualStatement(pstm);
            closeContextualConnection(conn);
        }
        return list;
    }

    public List<String> roles() {
        List<String> list = new ArrayList<String>();
        Connection conn = null;
        Statement pstm = null;
        try {
            String stm = "SELECT rol.nombre"
                    + " FROM " + getContextualDBObjectName(SecurityValues.TABLE_NAME_ROL);

            conn = dataSourceContext.getConnection();
            pstm = conn.createStatement();
            ResultSet result = pstm.executeQuery(stm);
            while (result.next()) {
                list.add(result.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger("carnico").log(Level.SEVERE, null, ex);
        } finally {
            //dataSourceContext.close(conn);
            closeContextualStatement(pstm);
            closeContextualConnection(conn);
        }
        return list;
    }

    public List<String> usuarios() {
        List<String> list = new ArrayList<String>();
        Connection conn = null;
        Statement pstm = null;
        try {
            String stm = "SELECT " + SecurityValues.TABLE_NAME_USER + ".login"
                    + " FROM " + getContextualDBObjectName(SecurityValues.TABLE_NAME_USER);

            conn = dataSourceContext.getConnection();
            pstm = conn.createStatement();
            ResultSet result = pstm.executeQuery(stm);
            while (result.next()) {
                list.add(result.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger("carnico").log(Level.SEVERE, null, ex);
        } finally {
            //dataSourceContext.close(conn);
            closeContextualStatement(pstm);
            closeContextualConnection(conn);
        }
        return list;
    }

    public boolean establoqueado(String usuario) {
        Connection conn = null;
        Statement pstm = null;
        try {
            String stm = "SELECT activo"
                    + " FROM " + getContextualDBObjectName(SecurityValues.TABLE_NAME_USER) + " where login='" + usuario + "'";

            conn = dataSourceContext.getConnection();
            pstm = conn.createStatement();
            ResultSet result = pstm.executeQuery(stm);
            if (result.next()) {
                return !result.getBoolean(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger("carnico").log(Level.SEVERE, null, ex);
        } finally {
            //dataSourceContext.close(conn);
            closeContextualStatement(pstm);
            closeContextualConnection(conn);
        }
        return false;
    }

    public List<String> usuarionMiembrosRol(String rol) {
        List<String> list = new ArrayList<String>();
        Connection conn = null;
        Statement pstm = null;
        try {
            String stm = "SELECT usuario_rol.id_usuario"
                    + " FROM " + getContextualDBObjectName(SecurityValues.TABLE_NAME_USER_ROL)
                    + " WHERE usuario_rol.id_rol = '"
                    + rol + "'";

            conn = dataSourceContext.getConnection();
            pstm = conn.createStatement();
            ResultSet result = pstm.executeQuery(stm);
            while (result.next()) {
                list.add(result.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger("carnico").log(Level.SEVERE, null, ex);
        } finally {
            //dataSourceContext.close(conn);
            closeContextualStatement(pstm);
            closeContextualConnection(conn);
        }
        return list;
    }

    public List<String> rolesUsuario(String usuario) {
        List<String> list = new ArrayList<String>();
        Connection conn = null;
        Statement pstm = null;
        try {
            String stm = "SELECT usuario_rol.id_rol"
                    + " FROM " + getContextualDBObjectName(SecurityValues.TABLE_NAME_USER_ROL) + " usuario_rol"
                    + " WHERE usuario_rol.id_usuario = '"
                    + usuario + "'";

            conn = dataSourceContext.getConnection();
            pstm = conn.createStatement();
            ResultSet result = pstm.executeQuery(stm);
            while (result.next()) {
                list.add(result.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger("carnico").log(Level.SEVERE, null, ex);
        } finally {
            //dataSourceContext.close(conn);
            closeContextualStatement(pstm);
            closeContextualConnection(conn);
        }
        return list;
    }

    public List<String> usuarionNoMiembrosRol(String rol) {
        List<String> list = new ArrayList<String>();
        Connection conn = null;
        Statement pstm = null;
        try {
            String stm1 = "SELECT usuario.login as login"
                    + " FROM " + getContextualDBObjectName(SecurityValues.TABLE_NAME_USER);
            String stm2 = "SELECT usuario_rol.id_usuario as login"
                    + " FROM " + getContextualDBObjectName("usuario_rol")
                    + " WHERE usuario_rol.id_rol = '"
                    + rol + "'";
            String stm = stm1 + " except " + stm2;

            conn = dataSourceContext.getConnection();
            pstm = conn.createStatement();
            ResultSet result = pstm.executeQuery(stm);
            while (result.next()) {
                list.add(result.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger("carnico").log(Level.SEVERE, null, ex);
        } finally {
            //dataSourceContext.close(conn);
            closeContextualStatement(pstm);
            closeContextualConnection(conn);
        }
        return list;
    }

    public List<String> rolesNoUsuario(String usuario) {
        List<String> list = new ArrayList<String>();
        Connection conn = null;
        Statement select = null;
        try {
            String stm1 = "SELECT rol.nombre as rol"
                    + " FROM " + getContextualDBObjectName(SecurityValues.TABLE_NAME_ROL)+" rol";
            String stm2 = "SELECT usuario_rol.id_rol as rol"
                    + " FROM " + getContextualDBObjectName(SecurityValues.TABLE_NAME_USER_ROL)+" usuario_rol"
                    + " WHERE usuario_rol.id_usuario = '"
                    + usuario + "'";
            String stm = stm1 + " except " + stm2;

            conn = dataSourceContext.getConnection();
            select = conn.createStatement();
            ResultSet result = select.executeQuery(stm);
            while (result.next()) {
                list.add(result.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger("carnico").log(Level.SEVERE, null, ex);
        } finally {
            //dataSourceContext.close(conn);
            closeContextualStatement(select);
            closeContextualConnection(conn);
        }
        return list;
    }

    public void insertarRol(String rol) {
        Connection conn = null;
        Statement select = null;
        try {
            String stm = "insert into " + getContextualDBObjectName(SecurityValues.TABLE_NAME_ROL) + "(descripcion, nombre)"
                    + "values('','" + rol + "')";

            conn = dataSourceContext.getConnection();
            select = conn.createStatement();
            select.executeUpdate(stm);
        } catch (SQLException ex) {
            Logger.getLogger("carnico").log(Level.SEVERE, null, ex);
        } finally {
            //dataSourceContext.close(conn);
            closeContextualStatement(select);
            closeContextualConnection(conn);
        }
    }

    public void AsignarUsuarios(List<String> usuarios, String rol) {
        Connection conn = null;
        Statement select = null;
        try {
            String stm = "delete from " + getContextualDBObjectName(SecurityValues.TABLE_NAME_USER_ROL)
                    + " where id_rol='" + rol + "'";

            conn = dataSourceContext.getConnection();
            conn.setAutoCommit(false);
            select = conn.createStatement();
            select.executeUpdate(stm);
            for (String usuario : usuarios) {
                stm = "insert into " + getContextualDBObjectName(SecurityValues.TABLE_NAME_USER_ROL) + "(id_usuario, id_rol)"
                        + "values('" + usuario + "','" + rol + "')";
                select = conn.createStatement();
                select.executeUpdate(stm);
            }
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger("carnico").log(Level.SEVERE, null, ex);
        } finally {
            //dataSourceContext.close(conn);
            closeContextualStatement(select);
            closeContextualConnection(conn);
        }
    }

    public void AsignarRoles(List<String> roles, String usuario) {
        Connection conn = null;
        Statement select = null;
        try {
            String stm = "delete from " + getContextualDBObjectName(SecurityValues.TABLE_NAME_USER_ROL)
                    + " where id_usuario='" + usuario + "'";

            conn = dataSourceContext.getConnection();
            conn.setAutoCommit(false);
            select = conn.createStatement();
            select.executeUpdate(stm);
            for (String rol : roles) {
                stm = "insert into " + getContextualDBObjectName(SecurityValues.TABLE_NAME_USER_ROL) + "(id_usuario, id_rol)"
                        + "values('" + usuario + "','" + rol + "')";
                select = conn.createStatement();
                select.executeUpdate(stm);
            }
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger("carnico").log(Level.SEVERE, null, ex);
        } finally {
            //dataSourceContext.close(conn);
            closeContextualStatement(select);
            closeContextualConnection(conn);
        }
    }
    
    private boolean validarUsuario(String usuario)
    {
        for(int i = 0;i < usuario.length();i++)
            if(Character.getType(usuario.charAt(i)) != Character.LOWERCASE_LETTER) return false;
        if(usuario.contains(" ")) return false; 
        return true;
    }

    public void insertarUsuario(String usuario, String nombre, String pass) throws DSecurityException {
        Connection conn = null;
        Statement select = null;
        if (!validatePassword(pass)) {
            throw new DSecurityException("La contraseña debe contener números y letras y debe tener mas de 8 caracteres");
        }
        
        if (!validarUsuario(usuario)) {
            throw new DSecurityException("El usuario debe estar compuesto de letras minusculas y no tener espacios");
        }
        try {
            String stm = "insert into " + getContextualDBObjectName(SecurityValues.TABLE_NAME_USER) + "(login, nombre, password, activo, login_fallidos,dias_caducar)"
                    + "values('" + usuario + "','"+nombre+"','" + pass + "',true, 0,90)";

            conn = dataSourceContext.getConnection();
            conn.setAutoCommit(false);
            select = conn.createStatement();
            select.executeUpdate(stm);
            passwordAlHistoria(conn, usuario, pass);
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger("carnico").log(Level.SEVERE, null, ex);
        } finally {
            //dataSourceContext.close(conn);
            closeContextualStatement(select);
            closeContextualConnection(conn);
        }
    }

    private void passwordAlHistoria(Connection conn, String usuario, String password) throws SQLException {
        String stm = "insert into " + getContextualDBObjectName(SecurityValues.TABLE_NAME_PASSWORD_HISTORY) + " (usuario, password, fecha)"
                + " values(?,?,?)";
        PreparedStatement pstm = conn.prepareStatement(stm);
        pstm.setString(1, usuario);
        pstm.setString(2, password);
        pstm.setDate(3, new Date(new java.util.Date().getTime()));
        pstm.executeUpdate();
    }

    private boolean validatePassword(String password) {
        Pattern p = Pattern.compile("[a-zA-Z]");
        Matcher m = p.matcher(password);
        boolean word = m.find();
        p = Pattern.compile("[0-9]");
        m = p.matcher(password);
        boolean digit = m.find();
        boolean len = password.length() >= 8;
        return word && digit && len;
    }

    private boolean repite(String usuario, String password) {
        Connection conn = null;
        PreparedStatement select = null;
        try {
            String stm = "select password, fecha from " + getContextualDBObjectName(SecurityValues.TABLE_NAME_PASSWORD_HISTORY) + " where usuario=? order by fecha desc limit 5";

            conn = dataSourceContext.getConnection();
            select = conn.prepareStatement(stm);
            select.setString(1, usuario);
            ResultSet rs = select.executeQuery();
            List<String> list = new ArrayList<String>();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            //@todo borrar las ultimas entradas, solo dejar las 5 ultimas
            for (String pass : list) {
                if (pass.equals(password)) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger("carnico").log(Level.SEVERE, null, ex);
        } finally {
            //dataSourceContext.close(conn);
            closeContextualStatement(select);
            closeContextualConnection(conn);
        }
        return false;
    }

    private void eliminarOldPassword(String usuario, int cant) {
        Connection conn = null;
        PreparedStatement select = null;
        try {
            String stm = "select fecha from " + getContextualDBObjectName(SecurityValues.TABLE_NAME_PASSWORD_HISTORY) + " where usuario=? order by fecha desc limit ?";

            conn = dataSourceContext.getConnection();
            select = conn.prepareStatement(stm);
            select.setString(1, usuario);
            select.setInt(2, cant);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
            }
            //@todo borrar las ultimas entradas, solo dejar las 5 ultimas
        } catch (SQLException ex) {
            Logger.getLogger("carnico").log(Level.SEVERE, null, ex);
        } finally {
            //dataSourceContext.close(conn);
            closeContextualStatement(select);
            closeContextualConnection(conn);
        }
    }

    public boolean passwordCaducada(String usuario) {
        Connection conn = null;
        Statement select = null;
        try {
            String stm = "select password_actualizada, dias_caducar from " + getContextualDBObjectName(SecurityValues.TABLE_NAME_USER)
                    + " where login='" + usuario + "'";

            conn = dataSourceContext.getConnection();
            select = conn.createStatement();
            ResultSet rs = new ResultSetWrapper(select.executeQuery(stm));
            if (rs.next()) {
                Date pa = rs.getDate("password_actualizada");
                java.util.Date fecha = new java.util.Date(rs.getDate(1).getTime() + rs.getLong(2) * 86400000);
                long time = new java.util.Date().getTime();
                long current = time;
                time = time % 86400000;
                time = current - time;
                if (fecha.before(new java.util.Date(time))) {
                    return true;
                } else {
                    return false;
                }
            }
            //@todo borrar las ultimas entradas, solo dejar las 5 ultimas
        } catch (SQLException ex) {
            Logger.getLogger("carnico").log(Level.SEVERE, null, ex);
        } finally {
            //dataSourceContext.close(conn);
            closeContextualStatement(select);
            closeContextualConnection(conn);
        }
        return false;
    }

    public void eliminarUsuario(String usuario) {
        Connection conn = null;
        Statement select = null;
        try {
            String stm = "delete from " + getContextualDBObjectName(SecurityValues.TABLE_NAME_USER_ROL)
                    + " where id_usuario='" + usuario + "';";
            stm += "delete from " + getContextualDBObjectName(SecurityValues.TABLE_NAME_USER)
                    + " where login='" + usuario + "'";

            conn = dataSourceContext.getConnection();
            select = conn.createStatement();
            select.executeUpdate(stm);
        } catch (SQLException ex) {
            Logger.getLogger("carnico").log(Level.SEVERE, null, ex);
        } finally {
            //dataSourceContext.close(conn);
            closeContextualStatement(select);
            closeContextualConnection(conn);
        }
    }

    public void desbloquearUsuario(String usuario) {
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            String stm = "update " + getContextualDBObjectName(SecurityValues.TABLE_NAME_USER) + " set activo=? where login=" + "'" + usuario + "'";

            conn = dataSourceContext.getConnection();
            pstm = conn.prepareStatement(stm);
            pstm.setBoolean(1, true);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger("carnico").log(Level.SEVERE, null, ex);
        } finally {
            //dataSourceContext.close(conn);
            closeContextualStatement(pstm);
            closeContextualConnection(conn);
        }
    }

    public void cambiarPassword(String usuario, String pass) throws DSecurityException {
        Connection conn = null;
        PreparedStatement select = null;
        if (!validatePassword(pass)) {
            throw new DSecurityException("La contraseña debe contener números y letras y debe tener mas de 8 caracteres");
        }
        if (repite(usuario, pass)) {
            throw new DSecurityException("La contraseña debe ser diferente de las anteriores ya usadas");
        }
        try {
            String stm = "update  " + getContextualDBObjectName(SecurityValues.TABLE_NAME_USER) + " set password='" + pass + "', password_actualizada=?"
                    + " where login='" + usuario + "'";

            conn = dataSourceContext.getConnection();
            conn.setAutoCommit(false);
            select = conn.prepareStatement(stm);
            select.setDate(1, new Date(new java.util.Date().getTime()));
            select.executeUpdate();
            passwordAlHistoria(conn, usuario, pass);
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger("carnico").log(Level.SEVERE, null, ex);
        } finally {
            //dataSourceContext.close(conn);
            closeContextualStatement(select);
            closeContextualConnection(conn);
        }
    }

    public void adicionarPermisoRol(List<Permission> permisos, String rol) {
        Connection conn = null;
        Statement select = null;
        try {
            String stm = "delete from " + getContextualDBObjectName(SecurityValues.TABLE_NAME_ROL_PERMISSION)
                    + " where id_rol='" + rol + "'";

            conn = dataSourceContext.getConnection();
            conn.setAutoCommit(false);
            select = conn.createStatement();
            select.executeUpdate(stm);
            for (Permission permiso : permisos) {
                stm = "select id from " + getContextualDBObjectName(SecurityValues.TABLE_NAME_PERMISSION)
                        + " where recurso='" + permiso.getName() + "' and accion='" + permiso.getActions() + "'";
                select = conn.createStatement();
                ResultSet result = select.executeQuery(stm);
                int id;
                if (result.next()) {
                    id = result.getInt(1);
                    stm = "insert into " + getContextualDBObjectName(SecurityValues.TABLE_NAME_ROL_PERMISSION) + "(id_permiso, id_rol)"
                            + "values('" + id + "','" + rol + "')";
                    select = conn.createStatement();
                    select.executeUpdate(stm);
                }
            }
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger("carnico").log(Level.SEVERE, null, ex);
        } finally {
            //dataSourceContext.close(conn);
            closeContextualStatement(select);
            closeContextualConnection(conn);
        }
    }

    public void adicionarPermisoUsuario(List<Permission> permisos, String usuario) {
        Connection conn = null;
        Statement select = null;
        try {
            String stm = "delete from " + getContextualDBObjectName(SecurityValues.TABLE_NAME_USER_PERMISSION) + ""
                    + " where id_usuario='" + usuario + "'";

            conn = dataSourceContext.getConnection();
            conn.setAutoCommit(false);
            select = conn.createStatement();
            select.executeUpdate(stm);
            for (Permission permiso : permisos) {
                stm = "select id from " + getContextualDBObjectName(SecurityValues.TABLE_NAME_PERMISSION)
                        + " where recurso='" + permiso.getName() + "' and accion='" + permiso.getActions() + "'";
                select = conn.createStatement();
                ResultSet result = select.executeQuery(stm);
                int id;
                if (result.next()) {
                    id = result.getInt(1);
                    stm = "insert into " + getContextualDBObjectName(SecurityValues.TABLE_NAME_USER_PERMISSION) + "(id_permiso, id_usuario)"
                            + "values('" + id + "','" + usuario + "')";
                    select = conn.createStatement();
                    select.executeUpdate(stm);
                }
            }
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger("carnico").log(Level.SEVERE, null, ex);
        } finally {
            //dataSourceContext.close(conn);
            closeContextualStatement(select);
            closeContextualConnection(conn);
        }
    }
    
    public String nombreUsuario(String usuario){
         Connection conn = null;
         Statement select = null;
        try {
            String stm = "select nombre from " + getContextualDBObjectName(SecurityValues.TABLE_NAME_USER)
                    + " where login='" + usuario + "'";

            conn = dataSourceContext.getConnection();
            conn.setAutoCommit(false);
            select = conn.createStatement();
            ResultSet result = select.executeQuery(stm);
            if(result.next())
                return result.getString(1);
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger("carnico").log(Level.SEVERE, null, ex);
        } finally {
            //dataSourceContext.close(conn);
            closeContextualStatement(select);
            closeContextualConnection(conn);
        }
        return "";
    }
    // </editor-fold>
}
