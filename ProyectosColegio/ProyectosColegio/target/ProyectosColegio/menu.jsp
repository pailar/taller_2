<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelo.Usuario"%>
<%
    // Si no hay sesion, volver al login
    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Menu principal</title>
</head>
<body>
    <h1>Menu</h1>
    <p>Hola, <b><%= u.getNombre() %></b></p>

    <form action="controlador" method="get">
        <input type="hidden" name="accion" value="proyectos">
        <input type="submit" value="Proyectos">
    </form>

    <form action="controlador" method="get">
        <input type="hidden" name="accion" value="salir">
        <input type="submit" value="Salir">
    </form>
</body>
</html>
