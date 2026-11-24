<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Gestión de Proyectos</title>
    <style>
        * {
            box-sizing: border-box;
        }
        body {
            margin: 0;
            font-family: Arial, Helvetica, sans-serif;
            color: #fff;
        }
        .portada {
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            text-align: center;
            padding: 24px;
            background: linear-gradient(rgba(0, 0, 0, 0.55), rgba(0, 0, 0, 0.7)),
                        url("img/campus.png") center center / cover no-repeat;
        }
        .contenido {
            max-width: 640px;
        }
        .etiqueta {
            margin: 0 0 16px 0;
            font-size: 13px;
            letter-spacing: 3px;
            text-transform: uppercase;
            opacity: 0.85;
        }
        h1 {
            margin: 0 0 16px 0;
            font-size: 44px;
            line-height: 1.15;
        }
        .texto {
            margin: 0 0 32px 0;
            font-size: 18px;
            line-height: 1.5;
            opacity: 0.92;
        }
        .btn-entrar {
            display: inline-block;
            padding: 14px 40px;
            background: #fff;
            color: #000;
            font-size: 16px;
            font-weight: bold;
            text-decoration: none;
            border-radius: 4px;
        }
        .btn-entrar:hover {
            background: #e6e6e6;
        }
        @media (max-width: 600px) {
            h1 {
                font-size: 32px;
            }
        }
    </style>
</head>
<body>
    <div class="portada">
        <div class="contenido">
            <h1>Gestión de Proyectos</h1>
            <p class="texto">
                Administra tus proyectos.
            </p>
            <a href="login.jsp" class="btn-entrar">Iniciar sesión</a>
        </div>
    </div>
</body>
</html>