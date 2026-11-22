package dao;

import conexion.Conexion;
import modelo.Linea;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LineasDAO {
    
        private final ObjetivosDAO objetivosDAO = new ObjetivosDAO();
        private ActividadesDAO actividadesDAO = new ActividadesDAO();

    private static final String SQL_RELACION =
              "SELECT r.id AS id_relacion, r.descripcion, l.id, l.nombre"
            + " FROM r_proyecto_linea r"
            + " INNER JOIN linea l ON r.id_linea = l.id";

    // Lineas genericas (para los select)
    public List<Linea> listarGenericas() {
        List<Linea> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM linea ORDER BY nombre";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Linea l = new Linea();
                l.setId(rs.getInt("id"));
                l.setNombre(rs.getString("nombre"));
                lista.add(l);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar lineas: " + e.getMessage());
        }
        return lista;
    }

    // Lineas detalladas de un proyecto; si buscar trae texto filtra por descripcion
    public List<Linea> listarPorProyecto(int idProyecto, String buscar) {
        List<Linea> lista = new ArrayList<>();
        boolean filtra = (buscar != null && !buscar.trim().isEmpty());
        String sql = SQL_RELACION + " WHERE r.id_proyecto = ?"
                   + (filtra ? " AND r.descripcion LIKE ?" : "")
                   + " ORDER BY r.id";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProyecto);
            if (filtra) {
                ps.setString(2, "%" + buscar.trim() + "%");
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(leer(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar lineas del proyecto: " + e.getMessage());
        }
        return lista;
    }

    public Linea obtenerPorRelacion(int idRelacion) {
        String sql = SQL_RELACION + " WHERE r.id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRelacion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return leer(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar linea: " + e.getMessage());
        }
        return null;
    }

    // Un proyecto no puede tener la misma linea generica dos veces (UNIQUE en la BD)
    public boolean existe(int idProyecto, int idLinea, int idRelacionExcluida) {
        String sql = "SELECT COUNT(*) FROM r_proyecto_linea WHERE id_proyecto = ? AND id_linea = ? AND id <> ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProyecto);
            ps.setInt(2, idLinea);
            ps.setInt(3, idRelacionExcluida);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar linea: " + e.getMessage());
            return false;
        }
    }

    public boolean agregar(int idProyecto, int idLinea, String descripcion) {
        String sql = "INSERT INTO r_proyecto_linea (id_proyecto, id_linea, descripcion) VALUES (?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProyecto);
            ps.setInt(2, idLinea);
            ps.setString(3, descripcion);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al agregar linea: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(int idRelacion, int idLinea, String descripcion) {
        String sql = "UPDATE r_proyecto_linea SET id_linea = ?, descripcion = ? WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idLinea);
            ps.setString(2, descripcion);
            ps.setInt(3, idRelacion);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar linea: " + e.getMessage());
            return false;
        }
    }

    // Sus objetivos y actividades se borran solos (ON DELETE CASCADE)
    public boolean eliminar(int idRelacion) {
        String sql = "DELETE FROM r_proyecto_linea WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRelacion);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar linea: " + e.getMessage());
            return false;
        }
    }

    private Linea leer(ResultSet rs) throws SQLException {
        Linea l = new Linea();
        l.setIdRelacion(rs.getInt("id_relacion"));
        l.setDescripcion(rs.getString("descripcion"));
        l.setId(rs.getInt("id"));
        l.setNombre(rs.getString("nombre"));
        return l;
    }
}