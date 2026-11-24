<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
    <link rel="stylesheet" href="css/estilo.css">
</head>
<body class="centrado">

    <div class="tarjeta">
        <h1>Login</h1>
        <p class="sub">Ingresa tus datos para continuar</p>

        <% if (request.getParameter("error") != null) { %>
            <div class="msg error">Usuario o clave incorrectos</div>
        <% } %>

        <form action="LoginServlet" method="POST">
            <label>Usuario</label>
            <input type="text" name="usuario" required>

            <label>Clave</label>
            <input type="password" name="clave" required>

            <button type="submit" class="btn-ancho">Ingresar</button>
            <a href="index.jsp" class="btn contorno btn-ancho">Cancelar</a>
        </form>
    </div>

</body>
</html>