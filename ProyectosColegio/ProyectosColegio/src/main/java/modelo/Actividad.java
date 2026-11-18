/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDateTime;

/**
 *
 * @author usuario
 */
public class Actividad {
    int id;
    String nombre;
    LocalDateTime fechaHoraInicio;
    LocalDateTime fechaHoraTermino;

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

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraTermino() {
        return fechaHoraTermino;
    }

    public void setFechaHoraTermino(LocalDateTime fechaHoraTermino) {
        this.fechaHoraTermino = fechaHoraTermino;
    }

    @Override
    public String toString() {
        return "Actividad{" + "id=" + id + ", nombre=" + nombre + ", fechaHoraInicio=" + fechaHoraInicio + ", fechaHoraTermino=" + fechaHoraTermino + '}';
    }
    
    
    
}
