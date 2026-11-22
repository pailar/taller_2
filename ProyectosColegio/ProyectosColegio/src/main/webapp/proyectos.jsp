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
<html>
<head>
    <meta charset="UTF-8">
    <title>Proyectos</title>
</head>
<body>
    <b>PROYECTOS</b>
    <br><br>

    <% if (request.getParameter("ok") != null) { %>
        <p><b>Operación realizada correctamente.</b></p>
    <% } %>
    <% if ("2".equals(request.getParameter("error"))) { %>
        <p><b>La fecha de término no puede ser anterior a la fecha de inicio.</b></p>
    <% } else if (request.getParameter("error") != null) { %>
        <p><b>No se pudo completar la operación.</b></p>
    <% } %>

    <%
        String buscar = (String) request.getAttribute("buscar");
    %>
    <form action="ControladorProyecto" method="get">
        <input type="hidden" name="accion" value="listar">
        <b>Buscar:</b>
        <input type="text" name="buscar" value="<%= esc(buscar) %>">
        <input type="submit" value="Buscar">
    </form>
    <br>

    <table border="1" cellpadding="5" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Descripción</th>
                <th>Objetivo</th>
                <th>Responsable</th>
                <th>Fecha inicio</th>
                <th>Fecha término</th>
                <th>Editar</th>
                <th>Quitar</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Proyecto> lista = (List<Proyecto>) request.getAttribute("proyectos");
                if (lista != null) {
                    for (Proyecto p : lista) {
            %>
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
                    </td>
                    <td>
                        <form action="ControladorProyecto" method="get">
                            <input type="hidden" name="accion" value="confirmarEliminar">
                            <input type="hidden" name="id" value="<%= p.getId() %>">
                            <input type="submit" value="Quitar">
                        </form>
                    </td>
                </tr>
            <%
                    }
                }
            %>
        </tbody>
    </table>
    <br>

    <form action="ControladorProyecto" method="get">
        <input type="hidden" name="accion" value="nuevo">
        <input type="submit" value="Agregar Proyecto">
    </form>

    <form action="menu.jsp" method="get">
        <input type="submit" value="Volver">
    </form>
</body>
</html>