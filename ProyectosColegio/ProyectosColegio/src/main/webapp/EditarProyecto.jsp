<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Proyecto</title>
</head>
<body>
    <h1>Editar Proyecto</h1>
    
    <form action="ControladorEditar" method="POST">
        <p>
            <label>ID:</label><br>
            <input type="number" name="txtId">
        </p>
        <p>
            <label>Nombre:</label><br>
            <input type="text" name="txtNombre">
        </p>
        <p>
            <label>Descripción:</label><br>
            <textarea name="txtDescripcion"></textarea>
        </p>
        <p>
            <label>Fecha de Inicio:</label><br>
            <input type="date" name="txtFechaInicio">
        </p>
        <p>
            <label>Fecha de Término:</label><br>
            <input type="date" name="txtFechaTermino">
        </p>
        <button type="submit">Guardar</button>
    </form>

    <form action="proyectos.jsp" method="get" style="margin-top: 10px;">
        <button type="submit">Cancelar</button>
    </form>
</body>
</html>