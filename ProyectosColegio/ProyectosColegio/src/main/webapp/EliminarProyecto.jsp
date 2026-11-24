<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.format.DateTimeFormatter"%>
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
        <link rel="stylesheet" href="css/estilo.css">
<head>
    <meta charset="UTF-8">
    <title>Eliminar Proyecto</title>
</head>
<body>
    <%
        Proyecto proyectoEliminar = (Proyecto) request.getAttribute("proyectoEliminar");
    %>
    <big><b>¿SEGURO QUE QUIERES QUITAR ESTE PROYECTO?</b></big>
    <br><br>
    <p>
        <b>ID:</b> <%= proyectoEliminar.getId() %><br>
        <b>Nombre:</b> <%= esc(proyectoEliminar.getNombre()) %><br>
        <b>Descripción:</b> <%= esc(proyectoEliminar.getDescripcion()) %><br>
        <b>Responsable:</b> <%= esc(proyectoEliminar.getFuncionario().getNombre()) %><br>
        <b>Fecha inicio:</b> <%= fmt(proyectoEliminar.getFechaInicio()) %><br>
        <b>Fecha término:</b> <%= fmt(proyectoEliminar.getFechaTermino()) %>
    </p>
    <p>Se borrarán también sus líneas, objetivos y actividades.</p>

    <form action="ControladorProyecto" method="POST">
        <input type="hidden" name="accion" value="eliminar">
        <input type="hidden" name="id" value="<%= proyectoEliminar.getId() %>">
        <input type="submit" value="&#10003; Sí, quitar">
    </form>

    <form action="ControladorProyecto" method="get">
        <input type="hidden" name="accion" value="listar">
        <input type="submit" value="X No, cancelar">
    </form>
</body>
</html>