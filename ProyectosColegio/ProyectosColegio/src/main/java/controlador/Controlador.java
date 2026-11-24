package controlador;

import factoria.DAOFactory;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Usuario;
import dao.iUsuario;

@WebServlet(name = "Controlador", urlPatterns = {"/controlador"})
public class Controlador extends HttpServlet {

    private final iUsuario usuarioDAO = DAOFactory.getUsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesar(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesar(request, response);
    }

    private void procesar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "";
        }

        switch (accion) {
            case "login":
                login(request, response);
                break;
            case "menu":
                if (haySesion(request)) {
                    ir(request, response, "menu.jsp");
                } else {
                    response.sendRedirect("login.jsp");
                }
                break;
            case "proyectos":
                if (haySesion(request)) {
                    ir(request, response, "menu.jsp");
                } else {
                    response.sendRedirect("login.jsp");
                }
                break;
            case "salir":
                HttpSession sesion = request.getSession(false);
                if (sesion != null) {
                    sesion.invalidate();
                }
                response.sendRedirect("index.jsp");
                break;
            default:
                response.sendRedirect("index.jsp");
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");

        Usuario usuario = usuarioDAO.validar(login, pass);
        if (usuario != null) {
            request.getSession().setAttribute("usuario", usuario);
            ir(request, response, "menu.jsp");
        } else {
            request.setAttribute("error", "Usuario o clave incorrectos");
            ir(request, response, "login.jsp");
        }
    }

    private boolean haySesion(HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);
        return sesion != null && sesion.getAttribute("usuario") != null;
    }

    private void ir(HttpServletRequest request, HttpServletResponse response, String vista)
            throws ServletException, IOException {
        request.getRequestDispatcher(vista).forward(request, response);
    }
}
