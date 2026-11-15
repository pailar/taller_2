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
public class Proyectos {
    private int id;
    private String nombre;
    private String descripcion;
    private LocalDate fechainicio;
    private LocalDate fechatermino;

    public Proyectos() {
    }

    public Proyectos(int id, String nombre, String descripcion, LocalDate fechainicio, LocalDate fechatermino) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechainicio = fechainicio;
        this.fechatermino = fechatermino;
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

    public LocalDate getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(LocalDate fechainicio) {
        this.fechainicio = fechainicio;
    }

    public LocalDate getFechatermino() {
        return fechatermino;
    }

    public void setFechatermino(LocalDate fechatermino) {
        this.fechatermino = fechatermino;
    }

    @Override
    public String toString() {
        return "Proyectos{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", fechainicio=" + fechainicio + ", fechatermino=" + fechatermino + '}';
    }
    
    
   
}
