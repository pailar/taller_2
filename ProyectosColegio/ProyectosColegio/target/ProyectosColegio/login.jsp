<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #fff;
        }
        .main-container {
            display: flex;
            height: 100vh;
        }
        .left-image {
            flex: 1;
            background: #eee;
        }
        .left-image img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        .right-form {
            width: 450px;
            padding: 50px;
            display: flex;
            flex-direction: column;
            justify-content: center;
        }
        h2 {
            margin-top: 0;
            font-size: 24px;
        }
        .error-box {
            border: 1px solid #ccc;
            padding: 10px;
            margin-bottom: 20px;
            background: #fff;
            font-size: 14px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
            font-size: 14px;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 8px;
            box-sizing: border-box;
            border: 1px solid #ccc;
        }
        .btn-ingresar {
            width: 100%;
            background: black;
            color: white;
            padding: 10px;
            border: none;
            cursor: pointer;
            font-weight: bold;
            margin-top: 10px;
        }
        .btn-cancelar {
            width: 100%;
            background: white;
            color: black;
            padding: 10px;
            border: 1px solid #ccc;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
            display: block;
            box-sizing: border-box;
            margin-top: 10px;
        }
    </style>
</head>
<body>

    <div class="main-container">
        <!-- Lado izquierdo con la imagen -->
        <div class="left-image">
            <!-- Cambia 'universidad.jpg' por el nombre exacto de tu archivo si es diferente -->
            <img src="img/universidad.jpg" alt="Universidad">
        </div>

        <!-- Lado derecho con el formulario -->
        <div class="right-form">
            <h2>Login</h2>

            <% if (request.getParameter("error") != null) { %>
                <div class="error-box">
                    Usuario o clave incorrectos
                </div>
            <% } %>

            <form action="LoginServlet" method="POST">
                <div class="form-group">
                    <label>Usuario</label>
                    <input type="text" name="usuario" required>
                </div>

                <div class="form-group">
                    <label>Clave</label>
                    <input type="password" name="clave" required>
                </div>

                <button type="submit" class="btn-ingresar">Ingresar</button>
                <a href="index.jsp" class="btn-cancelar">Cancelar</a>
            </form>
        </div>
    </div>

</body>
</html>