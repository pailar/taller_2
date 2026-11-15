package controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Captura los datos del formulario de login.jsp
        String usuarioIngresado = request.getParameter("usuario");
        String claveIngresada = request.getParameter("clave");

        // Valida si son admin y 1234
        if ("admin".equals(usuarioIngresado) && "1234".equals(claveIngresada)) {
            // Si es correcto, redirige al menú principal
            response.sendRedirect("menu.jsp");
        } else {
            // Si falla, regresa al login con la alerta de error
            response.sendRedirect("login.jsp?error=1");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
}