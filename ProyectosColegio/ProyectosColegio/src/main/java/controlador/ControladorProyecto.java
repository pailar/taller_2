package controlador;

import dao.ProyectosDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Proyecto;

@WebServlet(name = "ControladorProyecto", urlPatterns = {"/ControladorProyecto"})
public class ControladorProyecto extends HttpServlet {

    private ProyectosDAO dao = new ProyectosDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {
            case "listar":
                List<Proyecto> lista = dao.listar();
                request.setAttribute("proyectos", lista);
                request.getRequestDispatcher("proyectos.jsp").forward(request, response);
                break;

            case "guardar":
                String nombre = request.getParameter("txtNombre");
                int idFuncionario = Integer.parseInt(request.getParameter("txtIdFuncionario"));

                Proyecto p = new Proyecto();
                p.setNombre(nombre);
                //p.setIdFuncionarioResponsable(idFuncionario);

                dao.guardar(p);
                response.sendRedirect("ControladorProyecto?accion=listar");
                break;

            case "editar":
                int idEditar = Integer.parseInt(request.getParameter("id"));
                Proyecto proyectoEditar = dao.obtenerPorId(idEditar);
                request.setAttribute("proyectoEditar", proyectoEditar);
                request.getRequestDispatcher("proyectos.jsp").forward(request, response);
                break;

            case "actualizar":
                int idAct = Integer.parseInt(request.getParameter("txtId"));
                String nombreAct = request.getParameter("txtNombre");
                int idFuncAct = Integer.parseInt(request.getParameter("txtIdFuncionario"));

                //Proyecto pAct = new Proyecto(idAct, nombreAct, idFuncAct);
                //dao.actualizar(pAct);
                //response.sendRedirect("ControladorProyecto?accion=listar");
                break;

            case "eliminar":
                int id = Integer.parseInt(request.getParameter("id"));
                dao.eliminar(id);
                response.sendRedirect("ControladorProyecto?accion=listar");
                break;

            default:
                response.sendRedirect("menu.jsp");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}