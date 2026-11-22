package controlador;

import dao.ActividadesDAO;
import dao.ObjetivosDAO;
import dao.ProyectosDAO;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Actividad;

@WebServlet(name = "ControladorActividad", urlPatterns = {"/ControladorActividad"})
public class ControladorActividad extends HttpServlet {

    private ActividadesDAO dao = new ActividadesDAO();
    private ObjetivosDAO objetivosDAO = new ObjetivosDAO();
    private ProyectosDAO proyectosDAO = new ProyectosDAO();

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
            case "nuevo":
                request.setAttribute("idProyecto", idProyecto);
                request.setAttribute("objetivos", objetivosDAO.listarPorProyecto(idProyecto));
                request.setAttribute("funcionarios", proyectosDAO.listarFuncionarios());
                request.getRequestDispatcher("CrearActividad.jsp").forward(request, response);
                break;

            case "crear":
                Actividad nueva = armarActividad(request);
                if (nueva == null) {
                    volver(response, idProyecto, "&error=1");
                } else if (!fechasOrdenadas(nueva)) {
                    volver(response, idProyecto, "&error=2");
                } else if (dao.agregar(nueva)) {
                    volver(response, idProyecto, "&ok=1");
                } else {
                    volver(response, idProyecto, "&error=1");
                }
                break;

            case "editar":
                Actividad actividadEditar = dao.obtenerPorId(Util.entero(request.getParameter("idActividad")));
                if (actividadEditar == null) {
                    volver(response, idProyecto, "&error=1");
                    break;
                }
                request.setAttribute("idProyecto", idProyecto);
                request.setAttribute("actividadEditar", actividadEditar);
                request.setAttribute("objetivos", objetivosDAO.listarPorProyecto(idProyecto));
                request.setAttribute("funcionarios", proyectosDAO.listarFuncionarios());
                request.getRequestDispatcher("EditarActividad.jsp").forward(request, response);
                break;

            case "actualizar":
                Actividad actividadAct = armarActividad(request);
                int idActividadAct = Util.entero(request.getParameter("idActividad"));
                if (actividadAct == null || idActividadAct < 0) {
                    volver(response, idProyecto, "&error=1");
                    break;
                }
                actividadAct.setId(idActividadAct);
                if (!fechasOrdenadas(actividadAct)) {
                    volver(response, idProyecto, "&error=2");
                } else if (dao.actualizar(actividadAct)) {
                    volver(response, idProyecto, "&ok=1");
                } else {
                    volver(response, idProyecto, "&error=1");
                }
                break;

            case "eliminar":
                if (dao.eliminar(Util.entero(request.getParameter("idActividad")))) {
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

    // Arma una Actividad con lo del formulario (crear y editar usan casi los mismos campos).
    // Devuelve null si falta algo obligatorio, una fecha es invalida o el % esta fuera de 0-100.
    private Actividad armarActividad(HttpServletRequest request) {
        String nombre = Util.texto(request.getParameter("txtNombre"));
        int idObjetivo = Util.entero(request.getParameter("txtIdObjetivo"));
        int idFuncionario = Util.entero(request.getParameter("txtIdFuncionario"));

        if (nombre == null || idObjetivo < 0 || idFuncionario < 0) {
            return null;
        }

        Actividad a = new Actividad();
        a.setNombre(nombre);
        a.setDescripcion(Util.texto(request.getParameter("txtDescripcion")));
        a.setIdObjetivo(idObjetivo);
        a.setObservacion(Util.texto(request.getParameter("txtObservacion")));
        a.getFuncionario().setId(idFuncionario);
        a.setPao(request.getParameter("chkPao") != null);

        try {
            a.setFechaHoraInicio(Util.fechaHora(request.getParameter("txtFechaHoraInicio")));
            a.setFechaHoraTermino(Util.fechaHora(request.getParameter("txtFechaHoraTermino")));
        } catch (DateTimeParseException e) {
            return null;
        }
        if (a.getFechaHoraInicio() == null || a.getFechaHoraTermino() == null) {
            return null;
        }

        // en "crear" el % no viene y parte en 0
        String paramPorcentaje = request.getParameter("txtPorcentaje");
        int porcentaje = (paramPorcentaje == null) ? 0 : Util.entero(paramPorcentaje);
        if (porcentaje < 0 || porcentaje > 100) {
            return null;
        }
        a.setPorcentaje(porcentaje);
        return a;
    }

    // El termino no puede ser anterior al inicio
    private boolean fechasOrdenadas(Actividad a) {
        return !a.getFechaHoraTermino().isBefore(a.getFechaHoraInicio());
    }

    private void volver(HttpServletResponse response, int idProyecto, String resultado) throws IOException {
        response.sendRedirect("ControladorProyecto?accion=editar&id=" + idProyecto + resultado + "#actividades");
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