/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author usuario
 */
public class Objetivo {
    int id;
    String nombre;
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

    public ArrayList<Actividad> getArActividades() {
        return arActividades;
    }

    public void setArActividades(ArrayList<Actividad> arActividades) {
        this.arActividades = arActividades;
    }

    @Override
    public String toString() {
        return "Objetivo{" + "id=" + id + ", nombre=" + nombre + ", arActividades=" + arActividades + '}';
    }


    
}
