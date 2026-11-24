package dao;

import modelo.Usuario;

public interface iUsuario {

    Usuario validar(String login, String pass);

}
