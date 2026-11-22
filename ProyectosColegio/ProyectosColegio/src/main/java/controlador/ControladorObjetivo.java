package controlador;

import dao.LineasDAO;
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
    private LineasDAO lineasDAO = new LineasDAO();

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
                Objetivo nuevo = armarObjetivo(request);
                if (nuevo != null && dao.agregar(nuevo)) {
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
                request.setAttribute("lineasProyecto", lineasDAO.listarPorProyecto(idProyecto, null));
                request.getRequestDispatcher("EditarObjetivo.jsp").forward(request, response);
                break;

            case "actualizar":
                Objetivo objetivoAct = armarObjetivo(request);
                int idObjetivoAct = Util.entero(request.getParameter("idObjetivo"));
                if (objetivoAct == null || idObjetivoAct < 0) {
                    volver(response, idProyecto, "&error=1");
                    break;
                }
                objetivoAct.setId(idObjetivoAct);
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

    // Arma un Objetivo con lo del formulario (crear y editar usan casi los mismos campos).
    // Devuelve null si falta algo obligatorio o un numero es invalido.
    private Objetivo armarObjetivo(HttpServletRequest request) {
        int idRelacion = Util.entero(request.getParameter("txtIdRelacion"));
        String nombre = Util.texto(request.getParameter("txtNombre"));
        String alias = Util.texto(request.getParameter("txtAlias"));

        if (idRelacion < 0 || nombre == null || alias == null) {
            return null;
        }

        // en "crear" estos campos no vienen: orden lo calcula el DAO y logro parte en 0
        String paramLogro = request.getParameter("txtLogro");
        int logro = (paramLogro == null) ? 0 : Util.entero(paramLogro);
        if (logro < 0 || logro > 100) {
            return null;
        }

        String paramOrden = request.getParameter("txtOrden");
        int orden = (paramOrden == null) ? 1 : Util.entero(paramOrden);
        if (orden < 1) {
            return null;
        }

        Objetivo o = new Objetivo();
        o.setIdRelacion(idRelacion);
        o.setNombre(nombre);
        o.setAlias(alias);
        o.setDescripcion(Util.texto(request.getParameter("txtDescripcion")));
        o.setEvidencias(Util.texto(request.getParameter("txtEvidencias")));
        o.setLogro(logro);
        o.setOrden(orden);
        return o;
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