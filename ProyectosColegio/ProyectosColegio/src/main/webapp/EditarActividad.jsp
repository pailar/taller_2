<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Actividad"%>
<%@page import="modelo.Funcionario"%>
<%@page import="modelo.Objetivo"%>
<%!
    private String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    private String fhInput(LocalDateTime d) {
        if (d == null) return "";
        return d.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }
%>
<!DOCTYPE html>
<html>
        <link rel="stylesheet" href="css/estilo.css">
<head>
    <meta charset="UTF-8">
    <title>Editar Actividad</title>
</head>
<body>
    <%
        int idProyecto = (Integer) request.getAttribute("idProyecto");
        Actividad actividadEditar = (Actividad) request.getAttribute("actividadEditar");
        List<Objetivo> objetivos = (List<Objetivo>) request.getAttribute("objetivos");
        List<Funcionario> funcionarios = (List<Funcionario>) request.getAttribute("funcionarios");
    %>
    <big><b>EDITAR ACTIVIDAD</b></big>
    <br><br>

    <form action="ControladorActividad" method="POST">
        <input type="hidden" name="accion" value="actualizar">
        <input type="hidden" name="idProyecto" value="<%= idProyecto %>">
        <input type="hidden" name="idActividad" value="<%= actividadEditar.getId() %>">

        <table style="text-align: left; width: 100%;" border="1" cellpadding="2" cellspacing="2">
            <tbody>
                <tr>
                    <td><b>NOMBRE</b></td>
                    <td><input type="text" name="txtNombre" size="60" maxlength="100"
                               value="<%= esc(actividadEditar.getNombre()) %>" required></td>
                </tr>
                <tr>
                    <td><b>DESCRIPCIÓN</b></td>
                    <td><textarea name="txtDescripcion" rows="3" cols="50" maxlength="255"><%= esc(actividadEditar.getDescripcion()) %></textarea></td>
                </tr>
                <tr>
                    <td><b>Seleccione Objetivo Específico</b></td>
                    <td>
                        <select name="txtIdObjetivo" required>
                            <% if (objetivos != null) {
                                   for (Objetivo o : objetivos) { %>
                                <option value="<%= o.getId() %>"
                                    <%= (o.getId() == actividadEditar.getIdObjetivo()) ? "selected" : "" %>>
                                    <%= esc(o.getAlias()) %>
                                </option>
                            <%     }
                               } %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><b>Fecha-Hora Inicio</b></td>
                    <td><input type="datetime-local" name="txtFechaHoraInicio"
                               value="<%= fhInput(actividadEditar.getFechaHoraInicio()) %>" required></td>
                </tr>
                <tr>
                    <td><b>Fecha-Hora Termino</b></td>
                    <td><input type="datetime-local" name="txtFechaHoraTermino"
                               value="<%= fhInput(actividadEditar.getFechaHoraTermino()) %>" required></td>
                </tr>
                <tr>
                    <td><b>PAO</b></td>
                    <td><input type="checkbox" name="chkPao" <%= actividadEditar.isPao() ? "checked" : "" %>></td>
                </tr>
                <tr>
                    <td><b>% AVANCE (0 a 100)</b></td>
                    <td><input type="number" name="txtPorcentaje" min="0" max="100"
                               value="<%= actividadEditar.getPorcentaje() %>" required></td>
                </tr>
                <tr>
                    <td><b>Observación Control Gestión</b></td>
                    <td><textarea name="txtObservacion" rows="3" cols="50" maxlength="255"><%= esc(actividadEditar.getObservacion()) %></textarea></td>
                </tr>
                <tr>
                    <td><b>Seleccione Responsable de Actividad</b></td>
                    <td>
                        <select name="txtIdFuncionario" required>
                            <% if (funcionarios != null) {
                                   for (Funcionario f : funcionarios) { %>
                                <option value="<%= f.getId() %>"
                                    <%= (f.getId() == actividadEditar.getFuncionario().getId()) ? "selected" : "" %>>
                                    <%= esc(f.getNombre()) %>
                                </option>
                            <%     }
                               } %>
                        </select>
                    </td>
                </tr>
            </tbody>
        </table>
        <br>
        <input type="submit" value="Guardar">
    </form>

    <form action="ControladorProyecto" method="get">
        <input type="hidden" name="accion" value="editar">
        <input type="hidden" name="id" value="<%= idProyecto %>">
        <input type="submit" value="Cancelar">
    </form>
</body>
</html>