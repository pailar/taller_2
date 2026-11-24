<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    private String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
%>
<%
    // nombre del usuario que inicio sesion (leo, matias o danae), con la primera letra en mayuscula
    Object usuarioSesion = session.getAttribute("usuario");
    String nombreUsuario = (usuarioSesion == null) ? "" : usuarioSesion.toString();
    if (!nombreUsuario.isEmpty()) {
        nombreUsuario = nombreUsuario.substring(0, 1).toUpperCase() + nombreUsuario.substring(1);
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Menú Principal</title>
    <link rel="stylesheet" href="css/estilo.css">
</head>
<body class="centrado">

    <div class="tarjeta">
        <h1>¡Bienvenido<%= nombreUsuario.isEmpty() ? "" : ", " + esc(nombreUsuario) %>!</h1>
        <p class="sub">Selecciona una opción para continuar</p>

        <form action="ControladorProyecto" method="get">
            <button type="submit" class="btn-ancho">Ir a Proyectos</button>
        </form>

        <form action="LoginServlet" method="get">
            <input type="hidden" name="accion" value="salir">
            <button type="submit" class="btn-ancho contorno">Cerrar sesión</button>
        </form>
    </div>

</body>
</html>