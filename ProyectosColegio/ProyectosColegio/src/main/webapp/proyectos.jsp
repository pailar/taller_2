<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestion Proyectos</title>
</head>
<body>
    <h1>Gestion Proyectos</h1>

    <p>
        <a href="menu.jsp"><button type="button">Volver al Menú</button></a>
    </p>

    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Fecha Inicio</th>
            <th>Fecha Término</th>
            <th>Acciones</th>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>
                <form action="EditarProyecto.jsp" method="get" style="display:inline;">
                    <button type="submit">Editar</button>
                </form>
                <form action="EliminarProyecto.jsp" method="get" style="display:inline; margin-left: 5px;">
                    <button type="submit">Eliminar</button>
                </form>
            </td>
        </tr>
    </table>
</body>
</html>