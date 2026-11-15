<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Eliminar Proyecto</title>
</head>
<body>
    <h1>Eliminar Proyecto</h1>
    <p>¿Estás seguro de que quieres eliminar este proyecto, con ID X?</p>
    
    <form action="ControladorEliminar" method="POST">
        <button type="submit">Eliminar</button>
    </form>

    <form action="proyectos.jsp" method="get" style="margin-top: 10px;">
        <button type="submit">Cancelar</button>
    </form>
</body>
</html>