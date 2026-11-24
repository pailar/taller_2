<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Proyecto"%>
<%!
    private String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    private String fmt(LocalDate d) {
        if (d == null) return "-";
        return d.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Proyectos</title>
    <link rel="stylesheet" href="css/estilo.css">
</head>
<body>
    <h1>Proyectos</h1>
    <p class="sub">Busca, agrega, edita o quita proyectos.</p>

    <% if (request.getParameter("ok") != null) { %>
        <div class="msg ok">Operación realizada correctamente.</div>
    <% } %>
    <% if ("2".equals(request.getParameter("error"))) { %>
        <div class="msg error">La fecha de término no puede ser anterior a la fecha de inicio.</div>
    <% } else if (request.getParameter("error") != null) { %>
        <div class="msg error">No se pudo completar la operación.</div>
    <% } %>

    <%
        String buscar = (String) request.getAttribute("buscar");
        List<Proyecto> lista = (List<Proyecto>) request.getAttribute("proyectos");
    %>

    <div class="barra">
        <form action="ControladorProyecto" method="get" class="buscador">
            <input type="hidden" name="accion" value="listar">
            <input type="text" name="buscar" placeholder="Buscar por nombre" value="<%= esc(buscar) %>">
            <input type="submit" value="Buscar">
        </form>

        <form action="ControladorProyecto" method="get">
            <input type="hidden" name="accion" value="nuevo">
            <input type="submit" value="Agregar Proyecto">
        </form>
    </div>

    <div class="scroll">
        <table class="tabla-ancha">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Descripción</th>
                    <th>Objetivo</th>
                    <th>Responsable</th>
                    <th>Fecha inicio</th>
                    <th>Fecha término</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <% if (lista == null || lista.isEmpty()) { %>
                    <tr>
                        <td colspan="8" class="vacio">No hay proyectos para mostrar.</td>
                    </tr>
                <% } else {
                       for (Proyecto p : lista) { %>
                    <tr>
                        <td><b><%= p.getId() %></b></td>
                        <td><b><%= esc(p.getNombre()) %></b></td>
                        <td><%= esc(p.getDescripcion()) %></td>
                        <td><%= esc(p.getObjetivo()) %></td>
                        <td><%= esc(p.getFuncionario().getNombre()) %></td>
                        <td><%= fmt(p.getFechaInicio()) %></td>
                        <td><%= fmt(p.getFechaTermino()) %></td>
                        <td>
                            <form action="ControladorProyecto" method="get">
                                <input type="hidden" name="accion" value="editar">
                                <input type="hidden" name="id" value="<%= p.getId() %>">
                                <input type="submit" value="Editar">
                            </form>
                            <form action="ControladorProyecto" method="get">
                                <input type="hidden" name="accion" value="confirmarEliminar">
                                <input type="hidden" name="id" value="<%= p.getId() %>">
                                <input type="submit" value="Quitar" class="rojo">
                            </form>
                        </td>
                    </tr>
                <%     }
                   } %>
            </tbody>
        </table>
    </div>

    <form action="menu.jsp" method="get">
        <input type="submit" value="Volver">
    </form>
</body>
</html>