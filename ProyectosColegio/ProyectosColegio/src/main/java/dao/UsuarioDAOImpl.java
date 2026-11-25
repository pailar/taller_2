package dao;

import conexion.Conexion;
import db.ConexionGenericaDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Usuario;
import servicios.FactoriaServiciosImpl;

public class UsuarioDAOImpl implements iUsuario {

    //uDAO.validar(String login, String pass)
    @Override
    public Usuario validar(String login, String pass) {
        String sql = "SELECT login, password FROM usuario WHERE login = ? AND password = ?";
        try
        {
            
            FactoriaServiciosImpl f = FactoriaServiciosImpl.getFactoria();
            
            ConexionGenericaDB cg = f.getConexionDB();
            
            //Connection con = Conexion.getConexion();
            Connection con = cg.getConexion();
        
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setString(1, login);
            ps.setString(2, pass);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario unUsuario = new Usuario();
                unUsuario.setLogin(rs.getString("login"));
                unUsuario.setPassword(rs.getString("password"));
                return unUsuario;
            }
            else
            {
                //no encontramos un usuario con esta combinatoria de login pasword en la base de datos
                //return null;
            }

        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
            e.printStackTrace();
        } catch (InstantiationException ex) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
}
