<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Funcionario"%>
<%@page import="modelo.Objetivo"%>
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
    <title>Crear Actividad</title>
</head>
<body>
    <%
        int idProyecto = (Integer) request.getAttribute("idProyecto");
        List<Objetivo> objetivos = (List<Objetivo>) request.getAttribute("objetivos");
        List<Funcionario> funcionarios = (List<Funcionario>) request.getAttribute("funcionarios");
    %>
    <big><b>CREAR ACTIVIDAD</b></big>
    <br><br>

    <% if (objetivos == null || objetivos.isEmpty()) { %>
        <p>Primero crea un objetivo específico (sección 3) para poder agregar actividades.</p>
    <% } else { %>
        <form action="ControladorActividad" method="POST">
            <input type="hidden" name="accion" value="crear">
            <input type="hidden" name="idProyecto" value="<%= idProyecto %>">

            <table style="text-align: left; width: 100%;" border="1" cellpadding="2" cellspacing="2">
                <tbody>
                    <tr>
                        <td><b>NOMBRE</b></td>
                        <td><input type="text" name="txtNombre" size="60" maxlength="100" required></td>
                    </tr>
                    <tr>
                        <td><b>DESCRIPCIÓN</b></td>
                        <td><textarea name="txtDescripcion" rows="3" cols="50" maxlength="255"></textarea></td>
                    </tr>
                    <tr>
                        <td><b>Seleccione Objetivo Específico</b></td>
                        <td>
                            <select name="txtIdObjetivo" required>
                                <% for (Objetivo o : objetivos) { %>
                                    <option value="<%= o.getId() %>"><%= esc(o.getAlias()) %></option>
                                <% } %>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Fecha-Hora Inicio</b></td>
                        <td><input type="datetime-local" name="txtFechaHoraInicio" required></td>
                    </tr>
                    <tr>
                        <td><b>Fecha-Hora Termino</b></td>
                        <td><input type="datetime-local" name="txtFechaHoraTermino" required></td>
                    </tr>
                    <tr>
                        <td><b>PAO</b></td>
                        <td><input type="checkbox" name="chkPao"></td>
                    </tr>
                    <tr>
                        <td><b>Observación Control Gestión</b></td>
                        <td><textarea name="txtObservacion" rows="3" cols="50" maxlength="255"></textarea></td>
                    </tr>
                    <tr>
                        <td><b>Seleccione Responsable de Actividad</b></td>
                        <td>
                            <select name="txtIdFuncionario" required>
                                <% if (funcionarios != null) {
                                       for (Funcionario f : funcionarios) { %>
                                    <option value="<%= f.getId() %>"><%= esc(f.getNombre()) %></option>
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
    <% } %>

    <form action="ControladorProyecto" method="get">
        <input type="hidden" name="accion" value="editar">
        <input type="hidden" name="id" value="<%= idProyecto %>">
        <input type="submit" value="Cancelar / Volver">
    </form>
</body>
</html>