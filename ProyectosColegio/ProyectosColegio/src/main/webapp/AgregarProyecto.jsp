<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Funcionario"%>
<%!
    private String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
%>
<!DOCTYPE html>
<html>
        <link rel="stylesheet" href="css/estilo.css">
<head>
    <meta charset="UTF-8">
    <title>Agregar Proyecto</title>
</head>
<body>
    <big><b>Agregar Proyecto</b></big>
    <br><br>

    <form action="ControladorProyecto" method="POST">
        <input type="hidden" name="accion" value="guardar">

        <table border="1" cellpadding="2" cellspacing="2">
            <tr>
                <td><b>Nombre Proyecto</b></td>
                <td><input type="text" name="txtNombre" size="60" maxlength="100" required></td>
            </tr>
            <tr>
                <td><b>Descripción</b></td>
                <td><textarea name="txtDescripcion" rows="3" cols="50" maxlength="500"></textarea></td>
            </tr>
            <tr>
                <td><b>Responsable</b></td>
                <td>
                    <select name="txtIdFuncionario" required>
                        <%
                            List<Funcionario> funcionarios = (List<Funcionario>) request.getAttribute("funcionarios");
                            if (funcionarios != null) {
                                for (Funcionario f : funcionarios) {
                        %>
                            <option value="<%= f.getId() %>"><%= esc(f.getNombre()) %></option>
                        <%
                                }
                            }
                        %>
                    </select>
                </td>
            </tr>
            <tr>
                <td><b>Objetivo Proyecto</b></td>
                <td><input type="text" name="txtObjetivo" size="60" maxlength="255"></td>
            </tr>
            <tr>
                <td><b>Fecha Inicio</b></td>
                <td><input type="date" name="txtFechaInicio" required></td>
            </tr>
            <tr>
                <td><b>Fecha Término</b></td>
                <td><input type="date" name="txtFechaTermino" required></td>
            </tr>
        </table>
        <br>
        <input type="submit" value="Agregar">
    </form>

    <form action="ControladorProyecto" method="get">
        <input type="hidden" name="accion" value="listar">
        <input type="submit" value="Volver">
    </form>
</body>
</html>