/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;

/**
 *
 * @author danae
 */
public class Actividad {
    private int id;
    private String descripcion;
    private String nombre;
    private LocalDate fecha;
    private Proyectos proyecto=new Proyectos();

    public Actividad() {
    }

    public Actividad(int id, String descripcion, String nombre, LocalDate fecha, Proyectos proyecto) {
        this.id = id;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.fecha = fecha;
        this.proyecto=proyecto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Proyectos getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyectos proyecto) {
        this.proyecto = proyecto;
    }

    @Override
    public String toString() {
        return "Actividad{" + "id=" + id + ", descripcion=" + descripcion + ", nombre=" + nombre + ", fecha=" + fecha + ", proyecto=" + proyecto + '}';
    }
}
