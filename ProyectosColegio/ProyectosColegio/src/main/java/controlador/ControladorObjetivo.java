package controlador;

import dao.ObjetivosDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Objetivo;

@WebServlet(name = "ControladorObjetivo", urlPatterns = {"/ControladorObjetivo"})
public class ControladorObjetivo extends HttpServlet {

    private ObjetivosDAO dao = new ObjetivosDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "";
        }

        int idProyecto = Util.entero(request.getParameter("idProyecto"));
        if (idProyecto < 0) {
            response.sendRedirect("ControladorProyecto?accion=listar&error=1");
            return;
        }

        switch (accion) {
            case "crear":
                int idRelacion = Util.entero(request.getParameter("txtIdRelacion"));
                String nombre = Util.texto(request.getParameter("txtNombre"));
                String alias = Util.texto(request.getParameter("txtAlias"));
                if (idRelacion < 0 || nombre == null || alias == null) {
                    volver(response, idProyecto, "&error=1");
                    break;
                }
                Objetivo nuevo = new Objetivo();
                nuevo.setIdRelacion(idRelacion);
                nuevo.setNombre(nombre);
                nuevo.setAlias(alias);
                nuevo.setDescripcion(Util.texto(request.getParameter("txtDescripcion")));
                if (dao.agregar(nuevo)) {
                    volver(response, idProyecto, "&ok=1");
                } else {
                    volver(response, idProyecto, "&error=1");
                }
                break;

            case "editar":
                Objetivo objetivoEditar = dao.obtenerPorId(Util.entero(request.getParameter("idObjetivo")));
                if (objetivoEditar == null) {
                    volver(response, idProyecto, "&error=1");
                    break;
                }
                request.setAttribute("idProyecto", idProyecto);
                request.setAttribute("objetivoEditar", objetivoEditar);
                request.getRequestDispatcher("EditarObjetivo.jsp").forward(request, response);
                break;

            case "actualizar":
                // Se carga el objetivo y solo se cambian nombre, alias y descripcion;
                // orden, logro, evidencias y linea quedan como estaban
                Objetivo objetivoAct = dao.obtenerPorId(Util.entero(request.getParameter("idObjetivo")));
                String nombreAct = Util.texto(request.getParameter("txtNombre"));
                String aliasAct = Util.texto(request.getParameter("txtAlias"));
                if (objetivoAct == null || nombreAct == null || aliasAct == null) {
                    volver(response, idProyecto, "&error=1");
                    break;
                }
                objetivoAct.setNombre(nombreAct);
                objetivoAct.setAlias(aliasAct);
                objetivoAct.setDescripcion(Util.texto(request.getParameter("txtDescripcion")));
                if (dao.actualizar(objetivoAct)) {
                    volver(response, idProyecto, "&ok=1");
                } else {
                    volver(response, idProyecto, "&error=1");
                }
                break;

            case "eliminar":
                if (dao.eliminar(Util.entero(request.getParameter("idObjetivo")))) {
                    volver(response, idProyecto, "&ok=1");
                } else {
                    volver(response, idProyecto, "&error=1");
                }
                break;

            default:
                volver(response, idProyecto, "");
                break;
        }
    }

    private void volver(HttpServletResponse response, int idProyecto, String resultado) throws IOException {
        response.sendRedirect("ControladorProyecto?accion=editar&id=" + idProyecto + resultado + "#objetivos");
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