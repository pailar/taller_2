package dao;

import conexion.Conexion;
import modelo.Proyecto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProyectosDAO {

    public List<Proyecto> listar() {
        List<Proyecto> lista = new ArrayList<>();
        String sql = "SELECT p.id,"
                   + "       p.nombre as nombreproyecto,"
                   + "       p.id_funcionario_responsable,"
                   + "       f.nombre as nombrefuncionario " 
                   + " FROM proyecto p"
                   + " inner join funcionario f on p.id_funcionario_responsable = f.id";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Proyecto p = new Proyecto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombreproyecto"));
                p.getFuncionario().setId(rs.getInt("id_funcionario_responsable"));
                p.getFuncionario().setNombre(rs.getString("nombrefuncionario"));
                
                //p.setIdFuncionarioResponsable(rs.getInt("id_funcionario_responsable"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar proyectos: " + e.getMessage());
        }
        return lista;
    }

    public boolean guardar(Proyecto p) {
        String sql = "INSERT INTO proyecto (nombre, id_funcionario_responsable) VALUES (?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getFuncionario().getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar proyecto: " + e.getMessage());
            return false;
        }
    }

    public Proyecto obtenerPorId(int id) {
        /*
    
        String sql = "SELECT id, nombre, id_funcionario_responsable FROM proyecto WHERE id = ?";
        try
        {
            Connection con = Conexion.getConexion();
        
             PreparedStatement ps = con.prepareStatement(sql)             ps.setInt(1, id);
            
                   ResultSet rs = ps.executeQuery()) {
                
                Proyecto p = new Proyecto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.getFuncionario().setId(rs.getInt("id_funcionario_responsable"));
                if (rs.next()) {
                    
                    return new Proyecto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("id_funcionario_responsable")
                    );
                }
            
        } catch (SQLException e) {
            System.out.println("Error al buscar proyecto: " + e.getMessage());
        }
        */
        return null;
    }

    public boolean actualizar(Proyecto p) {
        String sql = "UPDATE proyecto SET nombre = ?, id_funcionario_responsable = ? WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getFuncionario().getId());
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