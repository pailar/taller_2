package dao;

import conexion.Conexion;
import modelo.Actividad;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ActividadesDAO {

    private static final String SQL_BASE =
              "SELECT a.id, a.nombre, a.descripcion, a.id_objetivo, o.alias AS alias_objetivo,"
            + "       a.fecha_hora_inicio, a.fecha_hora_termino, a.pao, a.porcentaje,"
            + "       a.observacion_control_gestion, a.id_funcionario_responsable,"
            + "       f.nombre AS nombrefuncionario"
            + " FROM actividad a"
            + " INNER JOIN objetivo o ON a.id_objetivo = o.id"
            + " INNER JOIN r_proyecto_linea r ON o.id_r_proyecto_linea = r.id"
            + " LEFT JOIN funcionario f ON a.id_funcionario_responsable = f.id";

    public List<Actividad> listarPorProyecto(int idProyecto, String buscar) {
        List<Actividad> lista = new ArrayList<>();
        boolean filtra = (buscar != null && !buscar.trim().isEmpty());
        String sql = SQL_BASE + " WHERE r.id_proyecto = ?"
                   + (filtra ? " AND a.nombre LIKE ?" : "")
                   + " ORDER BY a.fecha_hora_inicio, a.id";
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
            System.out.println("Error al listar actividades: " + e.getMessage());
        }
        return lista;
    }

    public Actividad obtenerPorId(int id) {
        String sql = SQL_BASE + " WHERE a.id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return leer(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar actividad: " + e.getMessage());
        }
        return null;
    }

    public boolean agregar(Actividad a) {
        String sql = "INSERT INTO actividad (nombre, descripcion, id_objetivo, fecha_hora_inicio,"
                   + " fecha_hora_termino, pao, porcentaje, observacion_control_gestion, id_funcionario_responsable)"
                   + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getDescripcion());
            ps.setInt(3, a.getIdObjetivo());
            ponerFechaHora(ps, 4, a.getFechaHoraInicio());
            ponerFechaHora(ps, 5, a.getFechaHoraTermino());
            ps.setBoolean(6, a.isPao());
            ps.setInt(7, a.getPorcentaje());
            ps.setString(8, a.getObservacion());
            ps.setInt(9, a.getFuncionario().getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al agregar actividad: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Actividad a) {
        String sql = "UPDATE actividad SET nombre = ?, descripcion = ?, id_objetivo = ?,"
                   + " fecha_hora_inicio = ?, fecha_hora_termino = ?, pao = ?, porcentaje = ?,"
                   + " observacion_control_gestion = ?, id_funcionario_responsable = ?"
                   + " WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getDescripcion());
            ps.setInt(3, a.getIdObjetivo());
            ponerFechaHora(ps, 4, a.getFechaHoraInicio());
            ponerFechaHora(ps, 5, a.getFechaHoraTermino());
            ps.setBoolean(6, a.isPao());
            ps.setInt(7, a.getPorcentaje());
            ps.setString(8, a.getObservacion());
            ps.setInt(9, a.getFuncionario().getId());
            ps.setInt(10, a.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar actividad: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM actividad WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar actividad: " + e.getMessage());
            return false;
        }
    }

    private void ponerFechaHora(PreparedStatement ps, int posicion, LocalDateTime fecha) throws SQLException {
        if (fecha == null) {
            ps.setNull(posicion, Types.TIMESTAMP);
        } else {
            ps.setTimestamp(posicion, Timestamp.valueOf(fecha));
        }
    }

    private Actividad leer(ResultSet rs) throws SQLException {
        Actividad a = new Actividad();
        a.setId(rs.getInt("id"));
        a.setNombre(rs.getString("nombre"));
        a.setDescripcion(rs.getString("descripcion"));
        a.setIdObjetivo(rs.getInt("id_objetivo"));
        a.setAliasObjetivo(rs.getString("alias_objetivo"));

        Timestamp inicio = rs.getTimestamp("fecha_hora_inicio");
        if (inicio != null) {
            a.setFechaHoraInicio(inicio.toLocalDateTime());
        }
        Timestamp termino = rs.getTimestamp("fecha_hora_termino");
        if (termino != null) {
            a.setFechaHoraTermino(termino.toLocalDateTime());
        }

        a.setPao(rs.getBoolean("pao"));
        a.setPorcentaje(rs.getInt("porcentaje"));
        a.setObservacion(rs.getString("observacion_control_gestion"));
        a.getFuncionario().setId(rs.getInt("id_funcionario_responsable"));
        a.getFuncionario().setNombre(rs.getString("nombrefuncionario"));
        return a;
    }
}