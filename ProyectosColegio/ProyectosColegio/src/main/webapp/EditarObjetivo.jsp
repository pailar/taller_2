<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelo.Objetivo"%>
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
    <title>Editar Objetivo</title>
</head>
<body>
    <%
        int idProyecto = (Integer) request.getAttribute("idProyecto");
        Objetivo objetivoEditar = (Objetivo) request.getAttribute("objetivoEditar");
    %>
    <big><b>EDITAR OBJETIVO ESPECÍFICO</b></big>
    <br><br>

    <form action="ControladorObjetivo" method="POST">
        <input type="hidden" name="accion" value="actualizar">
        <input type="hidden" name="idProyecto" value="<%= idProyecto %>">
        <input type="hidden" name="idObjetivo" value="<%= objetivoEditar.getId() %>">

        <table style="text-align: left;" border="1" cellpadding="2" cellspacing="2">
            <tbody>
                <tr>
                    <td><b>NOMBRE</b></td>
                    <td><input type="text" name="txtNombre" size="60" maxlength="255"
                               value="<%= esc(objetivoEditar.getNombre()) %>" required></td>
                </tr>
                <tr>
                    <td><b>ALIAS</b></td>
                    <td><input type="text" name="txtAlias" maxlength="30"
                               value="<%= esc(objetivoEditar.getAlias()) %>" required></td>
                </tr>
                <tr>
                    <td><b>DESCRIPCIÓN</b></td>
                    <td><textarea name="txtDescripcion" rows="3" cols="50" maxlength="255"><%= esc(objetivoEditar.getDescripcion()) %></textarea></td>
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