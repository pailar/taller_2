package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionSqlite3 {

    private static ConexionSqlite3 instancia;
    private final String url = "jdbc:sqlite:" + System.getProperty("user.home") + "/solemne2_productos.db";

    private ConexionSqlite3() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static synchronized ConexionSqlite3 getInstancia() {
        if (instancia == null) {
            instancia = new ConexionSqlite3();
        }
        return instancia;
    }

    public Connection conectar() throws SQLException {
        Connection con = DriverManager.getConnection(url);
        try (Statement st = con.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON;");
        }
        return con;
    }
}