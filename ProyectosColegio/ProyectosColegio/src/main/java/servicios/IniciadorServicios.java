package servicios;

import db.ConexionSqlite3;
import java.sql.Connection;
import java.sql.Statement;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class IniciadorServicios implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("--- INICIALIZANDO TABLAS DE MASCOTAS ---");
/*
        String[] comandosSQL = {
            "DROP TABLE IF EXISTS MASCOTA",
            "DROP TABLE IF EXISTS ESPECIE",
            "CREATE TABLE ESPECIE (" +
            "    ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    NOMBRE TEXT NOT NULL," +
            "    ALIAS TEXT NOT NULL" +
            ")",
            "CREATE TABLE MASCOTA (" +
            "    ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    NOMBRE TEXT NOT NULL," +
            "    EDAD INTEGER NOT NULL," +
            "    ID_ESPECIE INTEGER NOT NULL," +
            "    FOREIGN KEY (ID_ESPECIE) REFERENCES ESPECIE(ID)" +
            ")",
            "INSERT INTO ESPECIE (ID, NOMBRE, ALIAS) VALUES (1, 'Canino', 'CAN')",
            "INSERT INTO ESPECIE (ID, NOMBRE, ALIAS) VALUES (2, 'Felino', 'FEL')",
            "INSERT INTO ESPECIE (ID, NOMBRE, ALIAS) VALUES (3, 'Exótico', 'EXO')",
            "INSERT INTO MASCOTA (ID, NOMBRE, EDAD, ID_ESPECIE) VALUES (1, 'Firulais', 4, 1)",
            "INSERT INTO MASCOTA (ID, NOMBRE, EDAD, ID_ESPECIE) VALUES (2, 'Michi', 2, 2)",
            "INSERT INTO MASCOTA (ID, NOMBRE, EDAD, ID_ESPECIE) VALUES (3, 'Washington', 1, 3)"
        };

        try (Connection con = ConexionSqlite3.getInstancia().conectar();
             Statement st = con.createStatement()) {

            for (String sql : comandosSQL) {
                st.execute(sql);
            }
            System.out.println("--- TABLAS DE MASCOTAS CONFIGURADAS EXITOSAMENTE ---");

        } catch (Exception e) {
            e.printStackTrace();
        }
*/
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}