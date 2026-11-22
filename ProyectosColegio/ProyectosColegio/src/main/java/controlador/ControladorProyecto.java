package controlador;

import dao.ActividadesDAO;
import dao.IntegrantesDAO;
import dao.LineasDAO;
import dao.ObjetivosDAO;
import dao.ProyectosDAO;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Funcionario;
import modelo.Linea;
import modelo.Proyecto;

@WebServlet(name = "ControladorProyecto", urlPatterns = {"/ControladorProyecto"})
public class ControladorProyecto extends HttpServlet {

    private ProyectosDAO dao = new ProyectosDAO();
    private IntegrantesDAO integrantesDAO = new IntegrantesDAO();
    private LineasDAO lineasDAO = new LineasDAO();
    private ObjetivosDAO objetivosDAO = new ObjetivosDAO();
    private ActividadesDAO actividadesDAO = new ActividadesDAO();

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
                String buscarLinea = request.getParameter("buscarLinea");
                String buscarActividad = request.getParameter("buscarActividad");
                request.setAttribute("proyectoEditar", proyectoEditar);
                request.setAttribute("funcionarios", dao.listarFuncionarios());
                request.setAttribute("integrantes", integrantesDAO.listar(idEditar));
                request.setAttribute("disponibles", integrantesDAO.listarDisponibles(idEditar));
                request.setAttribute("lineasGenericas", lineasDAO.listarGenericas());
                request.setAttribute("lineasProyecto", lineasDAO.listarPorProyecto(idEditar, buscarLinea));
                request.setAttribute("buscarLinea", buscarLinea);
                request.setAttribute("lineasTodas", lineasDAO.listarPorProyecto(idEditar, null));
                request.setAttribute("objetivos", objetivosDAO.listarPorProyecto(idEditar));
                request.setAttribute("actividades", actividadesDAO.listarPorProyecto(idEditar, buscarActividad));
                request.setAttribute("buscarActividad", buscarActividad);
                request.getRequestDispatcher("EditarProyecto.jsp").forward(request, response);
                break;

            case "actualizar":
                Proyecto pAct = armarProyecto(request);
                int idAct = entero(request.getParameter("txtId"));
                if (idAct < 0) {
                    response.sendRedirect("ControladorProyecto?accion=listar&error=1");
                    break;
                }
                if (pAct == null) {
                    volverAEditar(response, idAct, "&error=1");
                    break;
                }
                pAct.setId(idAct);
                if (!fechasOrdenadas(pAct)) {
                    volverAEditar(response, idAct, "&error=2");
                } else if (dao.actualizar(pAct)) {
                    volverAEditar(response, idAct, "&ok=1");
                } else {
                    volverAEditar(response, idAct, "&error=1");
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

            case "agregarIntegrante":
                int idProyAgr = entero(request.getParameter("idProyecto"));
                int idFuncAgr = entero(request.getParameter("idFuncionario"));
                if (idProyAgr < 0) {
                    response.sendRedirect("ControladorProyecto?accion=listar&error=1");
                    break;
                }
                if (idFuncAgr >= 0 && integrantesDAO.agregar(idProyAgr, idFuncAgr)) {
                    volverAEditar(response, idProyAgr, "&ok=1");
                } else {
                    volverAEditar(response, idProyAgr, "&error=1");
                }
                break;

            case "confirmarQuitarIntegrante":
                int idProyConf = entero(request.getParameter("idProyecto"));
                int idFuncConf = entero(request.getParameter("idFuncionario"));
                Funcionario integranteQuitar = integrantesDAO.obtenerFuncionario(idFuncConf);
                if (idProyConf < 0 || integranteQuitar == null) {
                    response.sendRedirect("ControladorProyecto?accion=listar&error=1");
                    break;
                }
                request.setAttribute("idProyecto", idProyConf);
                request.setAttribute("integranteQuitar", integranteQuitar);
                request.getRequestDispatcher("QuitarIntegrante.jsp").forward(request, response);
                break;

            case "quitarIntegrante":
                int idProyQui = entero(request.getParameter("idProyecto"));
                int idFuncQui = entero(request.getParameter("idFuncionario"));
                if (idProyQui < 0) {
                    response.sendRedirect("ControladorProyecto?accion=listar&error=1");
                    break;
                }
                if (integrantesDAO.quitar(idProyQui, idFuncQui)) {
                    volverAEditar(response, idProyQui, "&ok=1");
                } else {
                    volverAEditar(response, idProyQui, "&error=1");
                }
                break;

            case "crearLinea":
                int idProyCrear = entero(request.getParameter("idProyecto"));
                int idLineaCrear = entero(request.getParameter("txtIdLinea"));
                String descCrear = texto(request.getParameter("txtDescripcionLinea"));
                if (idProyCrear < 0) {
                    response.sendRedirect("ControladorProyecto?accion=listar&error=1");
                    break;
                }
                if (idLineaCrear < 0 || descCrear == null) {
                    volverAEditar(response, idProyCrear, "&error=1");
                } else if (lineasDAO.existe(idProyCrear, idLineaCrear, 0)) {
                    volverAEditar(response, idProyCrear, "&error=3");
                } else if (lineasDAO.agregar(idProyCrear, idLineaCrear, descCrear)) {
                    volverAEditar(response, idProyCrear, "&ok=1");
                } else {
                    volverAEditar(response, idProyCrear, "&error=1");
                }
                break;

            case "editarLinea":
                int idProyEdLin = entero(request.getParameter("idProyecto"));
                int idRelEdLin = entero(request.getParameter("idRelacion"));
                Linea lineaEditar = lineasDAO.obtenerPorRelacion(idRelEdLin);
                if (idProyEdLin < 0 || lineaEditar == null) {
                    response.sendRedirect("ControladorProyecto?accion=listar&error=1");
                    break;
                }
                request.setAttribute("idProyecto", idProyEdLin);
                request.setAttribute("lineaEditar", lineaEditar);
                request.setAttribute("lineasGenericas", lineasDAO.listarGenericas());
                request.getRequestDispatcher("EditarLinea.jsp").forward(request, response);
                break;

            case "actualizarLinea":
                int idProyActLin = entero(request.getParameter("idProyecto"));
                int idRelActLin = entero(request.getParameter("idRelacion"));
                int idLineaAct = entero(request.getParameter("txtIdLinea"));
                String descAct = texto(request.getParameter("txtDescripcionLinea"));
                if (idProyActLin < 0) {
                    response.sendRedirect("ControladorProyecto?accion=listar&error=1");
                    break;
                }
                if (idRelActLin < 0 || idLineaAct < 0 || descAct == null) {
                    volverAEditar(response, idProyActLin, "&error=1");
                } else if (lineasDAO.existe(idProyActLin, idLineaAct, idRelActLin)) {
                    volverAEditar(response, idProyActLin, "&error=3");
                } else if (lineasDAO.actualizar(idRelActLin, idLineaAct, descAct)) {
                    volverAEditar(response, idProyActLin, "&ok=1");
                } else {
                    volverAEditar(response, idProyActLin, "&error=1");
                }
                break;

            case "borrarLinea":
                int idProyBor = entero(request.getParameter("idProyecto"));
                int idRelBor = entero(request.getParameter("idRelacion"));
                if (idProyBor < 0) {
                    response.sendRedirect("ControladorProyecto?accion=listar&error=1");
                    break;
                }
                if (lineasDAO.eliminar(idRelBor)) {
                    volverAEditar(response, idProyBor, "&ok=1");
                } else {
                    volverAEditar(response, idProyBor, "&error=1");
                }
                break;

            default:
                response.sendRedirect("menu.jsp");
                break;
        }
    }

    private void volverAEditar(HttpServletResponse response, int idProyecto, String resultado)
            throws IOException {
        response.sendRedirect("ControladorProyecto?accion=editar&id=" + idProyecto + resultado);
    }

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

    private boolean fechasOrdenadas(Proyecto p) {
        if (p.getFechaInicio() == null || p.getFechaTermino() == null) {
            return true;
        }
        return !p.getFechaTermino().isBefore(p.getFechaInicio());
    }

    private int entero(String valor) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private LocalDate fecha(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(valor.trim());
    }

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