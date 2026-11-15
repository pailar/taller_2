/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author danae
 */
public class Lineas {
    private int id;
    private String nombre;
    private String descripcion;
    private Proyectos proyecto=new Proyectos();

    public Lineas() {
    }

    public Lineas(int id, String nombre, String descripcion, Proyectos proyecto) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.proyecto=proyecto;
    }

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Proyectos getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyectos proyecto) {
        this.proyecto = proyecto;
    }

    @Override
    public String toString() {
        return "Lineas{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", proyecto=" + proyecto + '}';
    }
    
    
    
    
}
