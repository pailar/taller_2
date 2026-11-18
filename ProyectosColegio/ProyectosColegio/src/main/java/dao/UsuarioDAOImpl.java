package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public Usuario validar(String login, String pass) {
        String sql = "SELECT id, nombre, login, pass FROM usuario WHERE login = ? AND pass = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setString(2, pass);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return leer(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Usuario buscarPorLogin(String login) {
        String sql = "SELECT id, nombre, login, pass FROM usuario WHERE login = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return leer(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Usuario leer(ResultSet rs) throws SQLException {
        /*
        return new Usuario(rs.getInt("id"), rs.getString("nombre"),
                rs.getString("login"), rs.getString("pass"));
        */
        return null;
    }
}
