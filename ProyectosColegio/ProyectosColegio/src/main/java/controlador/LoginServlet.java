package controlador;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    // Usuarios que pueden entrar (usuario -> clave)
    private static final Map<String, String> USUARIOS = new HashMap<>();

    static {
        USUARIOS.put("leo", "1234");
        USUARIOS.put("matias", "1234");
        USUARIOS.put("danae", "1234");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Captura los datos del formulario de login.jsp
        String usuarioIngresado = request.getParameter("usuario");
        String claveIngresada = request.getParameter("clave");

        if (usuarioIngresado != null) {
            usuarioIngresado = usuarioIngresado.trim().toLowerCase();
        }

        // Valida que el usuario exista y que la clave sea la suya
        if (usuarioIngresado != null && claveIngresada != null
                && claveIngresada.equals(USUARIOS.get(usuarioIngresado))) {

            // Se crea una sesion nueva; el filtro la usa para dejar pasar a las demas paginas
            HttpSession anterior = request.getSession(false);
            if (anterior != null) {
                anterior.invalidate();
            }
            request.getSession(true).setAttribute("usuario", usuarioIngresado);

            response.sendRedirect("menu.jsp");
        } else {
            // Si falla, regresa al login con la alerta de error
            response.sendRedirect("login.jsp?error=1");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Cerrar sesion: LoginServlet?accion=salir
        if ("salir".equals(request.getParameter("accion"))) {
            HttpSession sesion = request.getSession(false);
            if (sesion != null) {
                sesion.invalidate();
            }
            response.sendRedirect("index.jsp");
            return;
        }
        response.sendRedirect("login.jsp");
    }
}