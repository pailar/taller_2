/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author danae
 */
public class Objetivo {
    private int id;
    private String descripcion;
    private Proyectos proyecto=new Proyectos();

    public Objetivo() {
    }

    
    public Objetivo(int id, String descripcion, Proyectos proyecto) {
        this.id = id;
        this.descripcion = descripcion;
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

    public Proyectos getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyectos proyecto) {
        this.proyecto = proyecto;
    }

    @Override
    public String toString() {
        return "Objetivo{" + "id=" + id + ", descripcion=" + descripcion +  ", proyecto=" + proyecto + '}';
    }
    
    
    
    
    
}
