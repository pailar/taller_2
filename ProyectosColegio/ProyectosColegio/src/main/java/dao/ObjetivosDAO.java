package dao;

import conexion.Conexion;
import modelo.Objetivo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ObjetivosDAO {

    private static final String SQL_BASE =
              "SELECT o.id, o.nombre, o.alias, o.descripcion, o.orden, o.evidencias, o.logro,"
            + "       o.id_r_proyecto_linea, r.descripcion AS linea_descripcion"
            + " FROM objetivo o"
            + " INNER JOIN r_proyecto_linea r ON o.id_r_proyecto_linea = r.id";

    // Todos los objetivos del proyecto (de todas sus lineas detalladas)
    public List<Objetivo> listarPorProyecto(int idProyecto) {
        List<Objetivo> lista = new ArrayList<>();
        String sql = SQL_BASE + " WHERE r.id_proyecto = ? ORDER BY o.orden, o.id";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProyecto);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(leer(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar objetivos: " + e.getMessage());
        }
        return lista;
    }

    public Objetivo obtenerPorId(int id) {
        String sql = SQL_BASE + " WHERE o.id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return leer(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar objetivo: " + e.getMessage());
        }
        return null;
    }

    // El orden queda al final de los objetivos de esa linea detallada
    public boolean agregar(Objetivo o) {
        String sql = "INSERT INTO objetivo (nombre, alias, descripcion, orden, evidencias, logro, id_r_proyecto_linea)"
                   + " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion()) {
            int orden = siguienteOrden(con, o.getIdRelacion());
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, o.getNombre());
                ps.setString(2, o.getAlias());
                ps.setString(3, o.getDescripcion());
                ps.setInt(4, orden);
                ps.setString(5, o.getEvidencias());
                ps.setInt(6, o.getLogro());
                ps.setInt(7, o.getIdRelacion());
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al agregar objetivo: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Objetivo o) {
        String sql = "UPDATE objetivo SET nombre = ?, alias = ?, descripcion = ?, orden = ?,"
                   + " evidencias = ?, logro = ?, id_r_proyecto_linea = ? WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, o.getNombre());
            ps.setString(2, o.getAlias());
            ps.setString(3, o.getDescripcion());
            ps.setInt(4, o.getOrden());
            ps.setString(5, o.getEvidencias());
            ps.setInt(6, o.getLogro());
            ps.setInt(7, o.getIdRelacion());
            ps.setInt(8, o.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar objetivo: " + e.getMessage());
            return false;
        }
    }

    // Sus actividades se borran solas (ON DELETE CASCADE)
    public boolean eliminar(int id) {
        String sql = "DELETE FROM objetivo WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar objetivo: " + e.getMessage());
            return false;
        }
    }

    private int siguienteOrden(Connection con, int idRelacion) throws SQLException {
        String sql = "SELECT COALESCE(MAX(orden), 0) + 1 FROM objetivo WHERE id_r_proyecto_linea = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRelacion);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 1;
            }
        }
    }

    private Objetivo leer(ResultSet rs) throws SQLException {
        Objetivo o = new Objetivo();
        o.setId(rs.getInt("id"));
        o.setNombre(rs.getString("nombre"));
        o.setAlias(rs.getString("alias"));
        o.setDescripcion(rs.getString("descripcion"));
        o.setOrden(rs.getInt("orden"));
        o.setEvidencias(rs.getString("evidencias"));
        o.setLogro(rs.getInt("logro"));
        o.setIdRelacion(rs.getInt("id_r_proyecto_linea"));
        o.setLineaDescripcion(rs.getString("linea_descripcion"));
        return o;
    }
}