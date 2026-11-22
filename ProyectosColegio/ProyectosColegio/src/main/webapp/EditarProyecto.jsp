<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Funcionario"%>
<%@page import="modelo.Proyecto"%>
<%!
    private String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Proyecto</title>
</head>
<body>
    <big><b>EDITAR PROYECTO</b></big>
    <br><br>

    <%
        Proyecto proyectoEditar = (Proyecto) request.getAttribute("proyectoEditar");
        String fechaInicio = (proyectoEditar.getFechaInicio() != null) ? proyectoEditar.getFechaInicio().toString() : "";
        String fechaTermino = (proyectoEditar.getFechaTermino() != null) ? proyectoEditar.getFechaTermino().toString() : "";
    %>

    <form action="ControladorProyecto" method="POST">
        <input type="hidden" name="accion" value="actualizar">
        <input type="hidden" name="txtId" value="<%= proyectoEditar.getId() %>">

        <table border="1" cellpadding="2" cellspacing="2">
            <tr>
                <td><b>NOMBRE PROYECTO</b></td>
                <td><input type="text" name="txtNombre" size="60" maxlength="100"
                           value="<%= esc(proyectoEditar.getNombre()) %>" required></td>
            </tr>
            <tr>
                <td><b>DESCRIPCIÓN</b></td>
                <td><textarea name="txtDescripcion" rows="3" cols="50" maxlength="500"><%= esc(proyectoEditar.getDescripcion()) %></textarea></td>
            </tr>
            <tr>
                <td><b>RESPONSABLE</b></td>
                <td>
                    <select name="txtIdFuncionario" required>
                        <%
                            List<Funcionario> funcionarios = (List<Funcionario>) request.getAttribute("funcionarios");
                            if (funcionarios != null) {
                                for (Funcionario f : funcionarios) {
                        %>
                            <option value="<%= f.getId() %>"
                                <%= (f.getId() == proyectoEditar.getFuncionario().getId()) ? "selected" : "" %>>
                                <%= esc(f.getNombre()) %>
                            </option>
                        <%
                                }
                            }
                        %>
                    </select>
                </td>
            </tr>
            <tr>
                <td><b>OBJETIVO PROYECTO</b></td>
                <td><input type="text" name="txtObjetivo" size="60" maxlength="255"
                           value="<%= esc(proyectoEditar.getObjetivo()) %>"></td>
            </tr>
            <tr>
                <td><b>FECHA INICIO</b></td>
                <td><input type="date" name="txtFechaInicio" value="<%= fechaInicio %>" required></td>
            </tr>
            <tr>
                <td><b>FECHA TÉRMINO</b></td>
                <td><input type="date" name="txtFechaTermino" value="<%= fechaTermino %>" required></td>
            </tr>
        </table>
        <br>
        <input type="submit" value="Guardar Datos Proyecto">
    </form>

    <form action="ControladorProyecto" method="get">
        <input type="hidden" name="accion" value="listar">
        <input type="submit" value="Volver a proyectos (sin guardar)">
    </form>
</body>
</html>