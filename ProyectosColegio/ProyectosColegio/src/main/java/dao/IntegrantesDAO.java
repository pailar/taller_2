package dao;

import conexion.Conexion;
import modelo.Funcionario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IntegrantesDAO {

    public List<Funcionario> listar(int idProyecto) {
        String sql = "SELECT f.id, f.rut, f.nombre"
                   + " FROM r_proyecto_funcionario r"
                   + " INNER JOIN funcionario f ON r.id_funcionario = f.id"
                   + " WHERE r.id_proyecto = ? ORDER BY f.nombre";
        return consultar(sql, idProyecto);
    }

    public List<Funcionario> listarDisponibles(int idProyecto) {
        String sql = "SELECT id, rut, nombre FROM funcionario"
                   + " WHERE id NOT IN (SELECT id_funcionario FROM r_proyecto_funcionario WHERE id_proyecto = ?)"
                   + " ORDER BY nombre";
        return consultar(sql, idProyecto);
    }

    public Funcionario obtenerFuncionario(int idFuncionario) {
        String sql = "SELECT id, rut, nombre FROM funcionario WHERE id = ?";
        List<Funcionario> lista = consultar(sql, idFuncionario);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public boolean agregar(int idProyecto, int idFuncionario) {
        String sql = "INSERT INTO r_proyecto_funcionario (id_proyecto, id_funcionario) VALUES (?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProyecto);
            ps.setInt(2, idFuncionario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al agregar integrante: " + e.getMessage());
            return false;
        }
    }

    public boolean quitar(int idProyecto, int idFuncionario) {
        String sql = "DELETE FROM r_proyecto_funcionario WHERE id_proyecto = ? AND id_funcionario = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProyecto);
            ps.setInt(2, idFuncionario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al quitar integrante: " + e.getMessage());
            return false;
        }
    }

    private List<Funcionario> consultar(String sql, int parametro) {
        List<Funcionario> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, parametro);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Funcionario f = new Funcionario();
                    f.setId(rs.getInt("id"));
                    f.setRut(rs.getString("rut"));
                    f.setNombre(rs.getString("nombre"));
                    lista.add(f);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar integrantes: " + e.getMessage());
        }
        return lista;
    }
}