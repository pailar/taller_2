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
public class Linea {
    int id;
    String nombre;
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

    public ArrayList<Objetivo> getArObjetivos() {
        return arObjetivos;
    }

    public void setArObjetivos(ArrayList<Objetivo> arObjetivos) {
        this.arObjetivos = arObjetivos;
    }

    @Override
    public String toString() {
        return "Linea{" + "id=" + id + ", nombre=" + nombre + ", arObjetivos=" + arObjetivos + '}';
    }


    
    
}
