package modelo;

import java.time.LocalDateTime;

public class Actividad {
    int id;
    String nombre;
    String descripcion;
    int idObjetivo;
    String aliasObjetivo;
    LocalDateTime fechaHoraInicio;
    LocalDateTime fechaHoraTermino;
    boolean pao;
    int porcentaje;
    String observacion;
    Funcionario funcionario = new Funcionario();

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

    public int getIdObjetivo() {
        return idObjetivo;
    }

    public void setIdObjetivo(int idObjetivo) {
        this.idObjetivo = idObjetivo;
    }

    public String getAliasObjetivo() {
        return aliasObjetivo;
    }

    public void setAliasObjetivo(String aliasObjetivo) {
        this.aliasObjetivo = aliasObjetivo;
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

    public boolean isPao() {
        return pao;
    }

    public void setPao(boolean pao) {
        this.pao = pao;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public String toString() {
        return "Actividad{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion
                + ", idObjetivo=" + idObjetivo + ", fechaHoraInicio=" + fechaHoraInicio
                + ", fechaHoraTermino=" + fechaHoraTermino + ", pao=" + pao
                + ", porcentaje=" + porcentaje + ", funcionario=" + funcionario + '}';
    }
}