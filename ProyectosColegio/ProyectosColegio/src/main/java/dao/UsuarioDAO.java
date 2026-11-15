package dao;

import modelo.Usuario;

public interface UsuarioDAO {

    Usuario validar(String login, String pass);

    Usuario buscarPorLogin(String login);
}
