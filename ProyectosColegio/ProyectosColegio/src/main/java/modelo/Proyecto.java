package modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Proyecto {
    private int id;
    private String nombre;
    private String descripcion;
    private String objetivo;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
    private Funcionario funcionario = new Funcionario();
    private ArrayList<Linea> arLineas = new ArrayList();

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

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(LocalDate fechaTermino) {
        this.fechaTermino = fechaTermino;
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
        return "Proyecto{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion
                + ", objetivo=" + objetivo + ", fechaInicio=" + fechaInicio
                + ", fechaTermino=" + fechaTermino + ", funcionario=" + funcionario
                + ", arLineas=" + arLineas + '}';
    }
}