package dao;

import java.util.List;
import modelo.ExcepcionPersonalizada;

public interface iCrud<T> {
    List<T> listar() throws ExcepcionPersonalizada;
    T obtenerPorId(int id) throws ExcepcionPersonalizada;
    boolean agregar(T entidad) throws ExcepcionPersonalizada;
    boolean actualizar(T entidad) throws ExcepcionPersonalizada;
    boolean eliminar(int id) throws ExcepcionPersonalizada;
}