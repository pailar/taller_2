package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String DRIVER  = "com.mysql.cj.jdbc.Driver";
    private static final String URL     = "jdbc:mysql://localhost:3306/taller_proyectos"
            + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USUARIO = "root";      // <-cambiar por usiario sql
    private static final String CLAVE   = "root";      // <-cambiar para clave para sqlo

    private Conexion() {
    }

    public static Connection getConexion() throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontro el driver de MySQL", e);
        }
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
