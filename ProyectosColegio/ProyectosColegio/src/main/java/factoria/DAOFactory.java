package factoria;

import dao.UsuarioDAO;
import dao.UsuarioDAOImpl;

public class DAOFactory {

    private DAOFactory() {
    }

    public static UsuarioDAO getUsuarioDAO() {
        return new UsuarioDAOImpl();
    }
}
