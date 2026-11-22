package modelo;

import java.util.ArrayList;

public class Objetivo {
    int id;
    String nombre;
    String alias;
    String descripcion;
    int orden;
    String evidencias;
    int logro;
    int idRelacion;             // id en r_proyecto_linea (linea detallada a la que pertenece)
    String lineaDescripcion;    // descripcion de esa linea detallada
    ArrayList<Actividad> arActividades = new ArrayList();

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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getEvidencias() {
        return evidencias;
    }

    public void setEvidencias(String evidencias) {
        this.evidencias = evidencias;
    }

    public int getLogro() {
        return logro;
    }

    public void setLogro(int logro) {
        this.logro = logro;
    }

    public int getIdRelacion() {
        return idRelacion;
    }

    public void setIdRelacion(int idRelacion) {
        this.idRelacion = idRelacion;
    }

    public String getLineaDescripcion() {
        return lineaDescripcion;
    }

    public void setLineaDescripcion(String lineaDescripcion) {
        this.lineaDescripcion = lineaDescripcion;
    }

    public ArrayList<Actividad> getArActividades() {
        return arActividades;
    }

    public void setArActividades(ArrayList<Actividad> arActividades) {
        this.arActividades = arActividades;
    }

    @Override
    public String toString() {
        return "Objetivo{" + "id=" + id + ", nombre=" + nombre + ", alias=" + alias
                + ", descripcion=" + descripcion + ", orden=" + orden + ", evidencias=" + evidencias
                + ", logro=" + logro + ", idRelacion=" + idRelacion + ", arActividades=" + arActividades + '}';
    }
}