/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import dao.UsuarioDAOImpl;

/**
 *
 * @author usuario
 */
public class Usuario {
    private String login;
    private String password;

    public Usuario() {
    }

    public Usuario(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" + "login=" + login + ", password=" + password + '}';
    }
    
    public Usuario validar(String login, String pass)
    {
        UsuarioDAOImpl uDAO = new UsuarioDAOImpl();
        Usuario usuarioPaso = uDAO.validar(login, pass);
        /*
        Esto para dar la idea que dando un recado al controlador proveniente desde el DAO
        if (usuarioPaso==null)
        {
            //El validar del DAO no encontro un registro en la tabla usuario donde el login y password fuera el solicitado
        }
        else
        {
            System.out.println("Encontramos el usuario con login correcto "+ usuarioPaso);
        }
        */
        return usuarioPaso;
    }
}
