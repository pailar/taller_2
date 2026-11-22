package modelo;

import java.util.ArrayList;

public class Linea {
    int id;
    String nombre;
    int idRelacion;
    String descripcion;
    ArrayList<Objetivo> arObjetivos = new ArrayList();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdRelacion() {
        return idRelacion;
    }

    public void setIdRelacion(int idRelacion) {
        this.idRelacion = idRelacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArrayList<Objetivo> getArObjetivos() {
        return arObjetivos;
    }

    public void setArObjetivos(ArrayList<Objetivo> arObjetivos) {
        this.arObjetivos = arObjetivos;
    }

    @Override
    public String toString() {
        return "Linea{" + "id=" + id + ", nombre=" + nombre + ", idRelacion=" + idRelacion
                + ", descripcion=" + descripcion + ", arObjetivos=" + arObjetivos + '}';
    }
}