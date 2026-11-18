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
public class Proyecto {
    int id;
    String nombre;
    Funcionario funcionario;
    ArrayList<Linea> arLineas = new ArrayList();

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

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public ArrayList<Linea> getArLineas() {
        return arLineas;
    }

    public void setArLineas(ArrayList<Linea> arLineas) {
        this.arLineas = arLineas;
    }

    @Override
    public String toString() {
        return "Proyecto{" + "id=" + id + ", nombre=" + nombre + ", funcionario=" + funcionario + ", arLineas=" + arLineas + '}';
    }



    
    
    
}
