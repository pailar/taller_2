package factoria;

import dao.UsuarioDAOImpl;
import dao.iUsuario;

public class DAOFactory {

    private DAOFactory() {
    }

    public static iUsuario getUsuarioDAO() {
        return new UsuarioDAOImpl();
    }
}
