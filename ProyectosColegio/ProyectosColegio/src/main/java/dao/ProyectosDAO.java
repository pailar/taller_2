package dao;

import conexion.Conexion;
import modelo.Proyectos;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProyectosDAO {

    public List<Proyectos> listar() {
        List<Proyectos> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion, fecha_inicio, fecha_termino FROM proyecto";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Proyectos p = new Proyectos();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                
                // Manejo seguro de fechas por si vienen nulas en la BD
                Date fInicio = rs.getDate("fecha_inicio");
                if (fInicio != null) {
                    p.setFechainicio(fInicio.toLocalDate());
                }
                
                Date fTermino = rs.getDate("fecha_termino");
                if (fTermino != null) {
                    p.setFechatermino(fTermino.toLocalDate());
                }
                
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar proyectos: " + e.getMessage());
        }
        return lista;
    }

    public boolean guardar(Proyectos p) {
        String sql = "INSERT INTO proyecto (nombre, descripcion, fecha_inicio, fecha_termino) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setObject(3, p.getFechainicio());
            ps.setObject(4, p.getFechatermino());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar proyecto: " + e.getMessage());
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