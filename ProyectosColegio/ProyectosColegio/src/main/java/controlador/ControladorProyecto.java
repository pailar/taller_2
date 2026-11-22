package controlador;

import dao.ProyectosDAO;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");

        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {
            case "listar":
                String buscar = request.getParameter("buscar");
                request.setAttribute("proyectos", dao.listar(buscar));
                request.setAttribute("buscar", buscar);
                request.getRequestDispatcher("proyectos.jsp").forward(request, response);
                break;

            case "nuevo":
                request.setAttribute("funcionarios", dao.listarFuncionarios());
                request.getRequestDispatcher("AgregarProyecto.jsp").forward(request, response);
                break;

            case "guardar":
                Proyecto p = armarProyecto(request);
                if (p == null) {
                    response.sendRedirect("ControladorProyecto?accion=listar&error=1");
                } else if (!fechasOrdenadas(p)) {
                    response.sendRedirect("ControladorProyecto?accion=listar&error=2");
                } else if (dao.guardar(p)) {
                    response.sendRedirect("ControladorProyecto?accion=listar&ok=1");
                } else {
                    response.sendRedirect("ControladorProyecto?accion=listar&error=1");
                }
                break;

            case "editar":
                int idEditar = entero(request.getParameter("id"));
                Proyecto proyectoEditar = dao.obtenerPorId(idEditar);
                if (proyectoEditar == null) {
                    response.sendRedirect("ControladorProyecto?accion=listar&error=1");
                    break;
                }
                request.setAttribute("proyectoEditar", proyectoEditar);
                request.setAttribute("funcionarios", dao.listarFuncionarios());
                request.getRequestDispatcher("EditarProyecto.jsp").forward(request, response);
                break;

            case "actualizar":
                Proyecto pAct = armarProyecto(request);
                int idAct = entero(request.getParameter("txtId"));
                if (pAct == null || idAct < 0) {
                    response.sendRedirect("ControladorProyecto?accion=listar&error=1");
                    break;
                }
                pAct.setId(idAct);
                if (!fechasOrdenadas(pAct)) {
                    response.sendRedirect("ControladorProyecto?accion=listar&error=2");
                } else if (dao.actualizar(pAct)) {
                    response.sendRedirect("ControladorProyecto?accion=listar&ok=1");
                } else {
                    response.sendRedirect("ControladorProyecto?accion=listar&error=1");
                }
                break;

            case "confirmarEliminar":
                int idConfirmar = entero(request.getParameter("id"));
                Proyecto proyectoEliminar = dao.obtenerPorId(idConfirmar);
                if (proyectoEliminar == null) {
                    response.sendRedirect("ControladorProyecto?accion=listar&error=1");
                    break;
                }
                request.setAttribute("proyectoEliminar", proyectoEliminar);
                request.getRequestDispatcher("EliminarProyecto.jsp").forward(request, response);
                break;

            case "eliminar":
                int id = entero(request.getParameter("id"));
                if (dao.eliminar(id)) {
                    response.sendRedirect("ControladorProyecto?accion=listar&ok=1");
                } else {
                    response.sendRedirect("ControladorProyecto?accion=listar&error=1");
                }
                break;

            default:
                response.sendRedirect("menu.jsp");
                break;
        }
    }

    // Arma un Proyecto con lo que viene del formulario (agregar y editar usan los mismos campos).
    // Devuelve null si falta el nombre, el responsable o si una fecha es invalida.
    private Proyecto armarProyecto(HttpServletRequest request) {
        String nombre = request.getParameter("txtNombre");
        int idFuncionario = entero(request.getParameter("txtIdFuncionario"));

        if (nombre == null || nombre.trim().isEmpty() || idFuncionario < 0) {
            return null;
        }

        Proyecto p = new Proyecto();
        p.setNombre(nombre.trim());
        p.setDescripcion(texto(request.getParameter("txtDescripcion")));
        p.setObjetivo(texto(request.getParameter("txtObjetivo")));
        p.getFuncionario().setId(idFuncionario);

        try {
            p.setFechaInicio(fecha(request.getParameter("txtFechaInicio")));
            p.setFechaTermino(fecha(request.getParameter("txtFechaTermino")));
        } catch (DateTimeParseException e) {
            return null;
        }
        return p;
    }

    // La fecha de termino no puede ser anterior a la de inicio
    private boolean fechasOrdenadas(Proyecto p) {
        if (p.getFechaInicio() == null || p.getFechaTermino() == null) {
            return true;
        }
        return !p.getFechaTermino().isBefore(p.getFechaInicio());
    }

    // Convierte a int; si viene vacio o invalido devuelve -1
    private int entero(String valor) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // El input type="date" envia yyyy-MM-dd; vacio = sin fecha
    private LocalDate fecha(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(valor.trim());
    }

    // Texto vacio se guarda como null
    private String texto(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        return valor.trim();
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