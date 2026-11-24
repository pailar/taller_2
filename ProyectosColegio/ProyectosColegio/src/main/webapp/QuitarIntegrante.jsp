<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    <title>Confirmar</title>
</head>
<body>
    <%
        int idProyecto = (Integer) request.getAttribute("idProyecto");
        Funcionario integranteQuitar = (Funcionario) request.getAttribute("integranteQuitar");
    %>
    <big><b>¿SEGURO QUE QUIERES QUITAR ESTE INTEGRANTE DEL PROYECTO?</b></big>
    <br><br>
    <p><b>Integrante:</b> <%= esc(integranteQuitar.getNombre()) %></p>

    <form action="ControladorProyecto" method="POST">
        <input type="hidden" name="accion" value="quitarIntegrante">
        <input type="hidden" name="idProyecto" value="<%= idProyecto %>">
        <input type="hidden" name="idFuncionario" value="<%= integranteQuitar.getId() %>">
        <input type="submit" value="&#10003; Sí, quitar">
    </form>

    <form action="ControladorProyecto" method="get">
        <input type="hidden" name="accion" value="editar">
        <input type="hidden" name="id" value="<%= idProyecto %>">
        <input type="submit" value="X No, cancelar">
    </form>
</body>
</html>
