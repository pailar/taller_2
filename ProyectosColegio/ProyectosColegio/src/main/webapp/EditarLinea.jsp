<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Linea"%>
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
    <title>Editar Línea</title>
</head>
<body>
    <%
        int idProyecto = (Integer) request.getAttribute("idProyecto");
        Linea lineaEditar = (Linea) request.getAttribute("lineaEditar");
        List<Linea> lineasGenericas = (List<Linea>) request.getAttribute("lineasGenericas");
    %>
    <big><b>EDITAR LÍNEA</b></big>
    <br><br>

    <form action="ControladorProyecto" method="POST">
        <input type="hidden" name="accion" value="actualizarLinea">
        <input type="hidden" name="idProyecto" value="<%= idProyecto %>">
        <input type="hidden" name="idRelacion" value="<%= lineaEditar.getIdRelacion() %>">

        <table style="text-align: left;" border="1" cellpadding="2" cellspacing="2">
            <tbody>
                <tr>
                    <td><b>LINEA DE ACCIÓN</b></td>
                    <td>
                        <select name="txtIdLinea" required>
                            <% if (lineasGenericas != null) {
                                   for (Linea l : lineasGenericas) { %>
                                <option value="<%= l.getId() %>"
                                    <%= (l.getId() == lineaEditar.getId()) ? "selected" : "" %>>
                                    <%= esc(l.getNombre()) %>
                                </option>
                            <%     }
                               } %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><b>DESCRIPCIÓN</b></td>
                    <td><textarea name="txtDescripcionLinea" rows="3" cols="50" maxlength="255" required><%= esc(lineaEditar.getDescripcion()) %></textarea></td>
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