<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body>
    <div class="pagina">
        <div class="imagen"></div>

        <div class="panel">
            <h1>Login</h1>

            <% if (request.getAttribute("error") != null) { %>
                <div class="error"><%= request.getAttribute("error") %></div>
            <% } %>

            <form action="controlador" method="post">
                <input type="hidden" name="accion" value="login">

                <label for="login">Usuario</label>
                <input type="text" id="login" name="login" required>

                <label for="pass">Clave</label>
                <input type="password" id="pass" name="pass" required>

                <input type="submit" value="Ingresar">
            </form>

            <form action="index.jsp" method="get">
                <input type="submit" class="secundario" value="Cancelar">
            </form>

        </div>
    </div>
</body>
</html>
