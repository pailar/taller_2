package dao;

import conexion.Conexion;
import modelo.Funcionario;
import modelo.Proyecto;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProyectosDAO {

    private static final String SQL_BASE =
              "SELECT p.id,"
            + "       p.nombre as nombreproyecto,"
            + "       p.descripcion,"
            + "       p.objetivo,"
            + "       p.fecha_inicio,"
            + "       p.fecha_termino,"
            + "       p.id_funcionario_responsable,"
            + "       f.nombre as nombrefuncionario "
            + " FROM proyecto p"
            + " inner join funcionario f on p.id_funcionario_responsable = f.id";

    public List<Proyecto> listar() {
        return listar(null);
    }

    public List<Proyecto> listar(String buscar) {
        List<Proyecto> lista = new ArrayList<>();
        boolean filtra = (buscar != null && !buscar.trim().isEmpty());
        String sql = SQL_BASE + (filtra ? " WHERE p.nombre LIKE ?" : "") + " ORDER BY p.id";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (filtra) {
                ps.setString(1, "%" + buscar.trim() + "%");
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(leer(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar proyectos: " + e.getMessage());
        }
        return lista;
    }

    public List<Funcionario> listarFuncionarios() {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT id, rut, nombre FROM funcionario ORDER BY nombre";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setId(rs.getInt("id"));
                f.setRut(rs.getString("rut"));
                f.setNombre(rs.getString("nombre"));
                lista.add(f);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar funcionarios: " + e.getMessage());
        }
        return lista;
    }

    public boolean guardar(Proyecto p) {
        String sql = "INSERT INTO proyecto (nombre, descripcion, objetivo, fecha_inicio, fecha_termino, id_funcionario_responsable)"
                   + " VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setString(3, p.getObjetivo());
            ponerFecha(ps, 4, p.getFechaInicio());
            ponerFecha(ps, 5, p.getFechaTermino());
            ps.setInt(6, p.getFuncionario().getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al guardar proyecto: " + e.getMessage());
            return false;
        }
    }

    public Proyecto obtenerPorId(int id) {
        String sql = SQL_BASE + " WHERE p.id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return leer(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar proyecto: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(Proyecto p) {
        String sql = "UPDATE proyecto SET nombre = ?, descripcion = ?, objetivo = ?,"
                   + " fecha_inicio = ?, fecha_termino = ?, id_funcionario_responsable = ?"
                   + " WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setString(3, p.getObjetivo());
            ponerFecha(ps, 4, p.getFechaInicio());
            ponerFecha(ps, 5, p.getFechaTermino());
            ps.setInt(6, p.getFuncionario().getId());
            ps.setInt(7, p.getId());
            return ps.executeUpdate() > 0;
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
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar proyecto: " + e.getMessage());
            return false;
        }
    }

    private void ponerFecha(PreparedStatement ps, int posicion, LocalDate fecha) throws SQLException {
        if (fecha == null) {
            ps.setNull(posicion, Types.DATE);
        } else {
            ps.setDate(posicion, Date.valueOf(fecha));
        }
    }

    private Proyecto leer(ResultSet rs) throws SQLException {
        Proyecto p = new Proyecto();
        p.setId(rs.getInt("id"));
        p.setNombre(rs.getString("nombreproyecto"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setObjetivo(rs.getString("objetivo"));

        Date inicio = rs.getDate("fecha_inicio");
        if (inicio != null) {
            p.setFechaInicio(inicio.toLocalDate());
        }
        Date termino = rs.getDate("fecha_termino");
        if (termino != null) {
            p.setFechaTermino(termino.toLocalDate());
        }

        p.getFuncionario().setId(rs.getInt("id_funcionario_responsable"));
        p.getFuncionario().setNombre(rs.getString("nombrefuncionario"));
        return p;
    }
}