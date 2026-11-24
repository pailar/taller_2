package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Usuario;

public class UsuarioDAOImpl implements iUsuario {

    //uDAO.validar(String login, String pass)
    @Override
    public Usuario validar(String login, String pass) {
        String sql = "SELECT id, nombre, login, password FROM usuario WHERE login = ? AND pass = ?";
        try
        {
            Connection con = Conexion.getConexion();
        
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
            e.printStackTrace();
        }
        return null;
    }

    
}
