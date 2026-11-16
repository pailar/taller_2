package dao;

import conexion.Conexion;
import modelo.Proyectos;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProyectosDAO {

    public List<Proyectos> listar() {
        List<Proyectos> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, id_funcionario_responsable FROM proyecto";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Proyectos p = new Proyectos();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setIdFuncionarioResponsable(rs.getInt("id_funcionario_responsable"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar proyectos: " + e.getMessage());
        }
        return lista;
    }

    public boolean guardar(Proyectos p) {
        String sql = "INSERT INTO proyecto (nombre, id_funcionario_responsable) VALUES (?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getIdFuncionarioResponsable());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar proyecto: " + e.getMessage());
            return false;
        }
    }

    public Proyectos obtenerPorId(int id) {
        String sql = "SELECT id, nombre, id_funcionario_responsable FROM proyecto WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Proyectos(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("id_funcionario_responsable")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar proyecto: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(Proyectos p) {
        String sql = "UPDATE proyecto SET nombre = ?, id_funcionario_responsable = ? WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getIdFuncionarioResponsable());
            ps.setInt(3, p.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar proyecto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM proyecto WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar proyecto: " + e.getMessage());
            return false;
        }
    }
}